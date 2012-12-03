(ns abqfood.data
  (:require [clojure.data.xml :as xml]
            [clojure.java.io :as io]
            [clojure.string :as string]))

(def data-file "data/FoodInspectionsCABQ-en-us.xml")

(def inspection-data
  (->> (xml/parse (io/reader (io/resource data-file)))
       :content
       first))

(defn get-metadata []
  (with-open [f (io/reader (io/resource data-file))]
    (doall
     (->> (xml/parse f)
          :content
          (filter #(= :metadata (:tag %)))
          first
          :content
          (map #(-> %
                    (get-in [:attrs :name])
                    string/lower-case
                    (string/replace #"_" "-")
                    keyword))))))

(defn process-row [metadata row]
  (->> row
       :content
       (filter #(= :value (:tag %)))
       (map :content)
       (map #(apply str %))
       (map string/trim)
       (zipmap metadata)))

(defn get-inspections []
  (let [metadata (get-metadata)]
    (->> (xml/parse (io/reader (io/resource data-file)))
         :content
         (filter #(= :data (:tag %)))
         first
         :content
         (filter #(= :row (:tag %)))
         (map #(process-row metadata %)))))
