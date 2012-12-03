(ns abqfood.db
  (:require [datomic.api :refer [q db] :as d]
            [clojure.java.io :as io]))

(def db-uri "datomic:free://localhost:4334/abqfood")
(def conn (d/connect db-uri))

(defn transact-schema []
  @(d/transact conn (-> "data/schema.dtm"
                        io/resource
                        slurp
                        read-string)))

(defn make-datom [inspect]
  (->> inspect
       seq
       (map (fn [[k v]] [(keyword "inspect" (name k)) v]))
       (into {:db/id (d/tempid :db.part/user)})))

(defn transact-data [inspects]
  (doseq [datoms (partition-all 1000 (map make-datom inspects))]
    @(d/transact conn datoms)))



(defn get-facilities []
  (->> (q '[:find ?id ?name
            :where
            [?e :inspect/facility-id ?id]
            [?e :inspect/facility-name ?name]
            [?e :inspect/inspection-date ?date]]
          (db conn))
       (map (fn [[id name]] {:id id :name name :uri (str "/facility/" id)}))
       (sort-by :name)))

(defn get-inspections-by-facility
  [fid]
  (->> (q '[:find
            ?iid ?date ?result
            :in $ ?fid
            :where
            [?e :inspect/facility-id ?fid]
            [?e :inspect/serial-num ?iid]
            [?e :inspect/inspection-date ?date]
            [?e :inspect/result-desc ?result]]
          (db conn) fid)
       (map (fn [[id date result]]
              {:id id :date date :result result :facility_id fid}))))

(defn get-inspections-by-result
  [rid]
  (->> (q '[:find
            ?iid ?date ?facility ?fid
            :in $ ?rid
            :where
            [?e :inspect/result-code ?rid]
            [?e :inspect/facility-id ?fid]
            [?e :inspect/facility-name ?facility]
            [?e :inspect/serial-num ?iid]
            [?e :inspect/inspection-date ?date]]
          (db conn) rid)
       (map (fn [[id date facility fid]]
              {:id id :date date :facility facility :facility_id fid}))))

(defn get-inspections-by-date
  []
  (->> (q '[:find
            ?iid ?date ?facility ?fid ?result
            :where
            [?e :inspect/serial-num ?iid]
            [?e :inspect/inspection-date ?date]
            [(< "2012-09-31T00:00:00" ?date)]
            [?e :inspect/facility-name ?facility]
            [?e :inspect/facility-id ?fid]
            [?e :inspect/result-desc ?result]]
          (db conn))
       (map (fn [[iid date facility fid result]]
              {:id iid :date date :facility facility :facility_id fid :result result}))))

(defn get-inspection [fid iid]
  (let [dbval (db conn)]
    (->> (q '[:find
              ?e
              :in
              $ ?fid ?iid
              :where
              [?e :inspect/facility-id ?fid]
              [?e :inspect/serial-num ?iid]]
            dbval fid iid)
         (map (fn [[e]]
                (let [e (d/entity dbval e)]
                  {:id iid
                   :facility_id fid
                   :facility_name (:inspect/facility-name e)
                   :owner_name (:inspect/owner-name e)
                   :inspection_date (:inspect/inspection-date e)
                   :inspection_desc (:inspect/inspection-desc e)
                   :category (:inspect/program-category-description e)
                   :action_desc (:inspect/action-desc e)
                   :result_desc (:inspect/result-desc e)
                   :violation_desc (:inspect/violation-desc e)
                   :memo (:inspect/inspection-memo e)})))
         first)))

(defn get-results []
  (->> (q '[:find
            ?rid ?name
            :where
            [?e :inspect/result-code ?rid]
            [?e :inspect/result-desc ?name]]
          (db conn))
       (map (fn [[id name]] {:id id :name name}))))

