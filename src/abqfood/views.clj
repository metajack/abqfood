(ns abqfood.views
  (:require [abqfood.db :as db]))

(defn facilities [req]
  (db/get-facilities))

(defn inspections-by-facility
  [req fid]
  (db/get-inspections-by-facility fid))

(defn inspections-by-result
  [req rid]
  (db/get-inspections-by-result rid))

(defn inspections-by-date
  [req]
  (db/get-inspections-by-date))

(defn inspection [req fid iid]
  (db/get-inspection fid iid))

(defn results [req]
  (db/get-results))