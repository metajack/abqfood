(defproject abqfood "1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [org.clojure/data.json "0.2.0"]
                 [com.datomic/datomic-free "0.8.3627"]
                 [compojure "1.1.3"]
                 [ring/ring-devel "1.1.6"]
                 [enlive "1.0.1"]
                 [org.clojure/data.xml "0.0.6"]
                 [org.clojars.metajack/ring-gzip-middleware "0.2.0"]]
  :plugins [[lein-ring "0.7.5"]]
  :ring {:handler abqfood.core/app})
