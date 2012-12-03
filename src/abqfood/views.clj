(ns abqfood.views
  (:require [abqfood.db :as db]))

(defn facilities [req]
  (db/get-facilities))

(defn inspections [req fid]
  (db/get-inspections fid))

(defn inspection [req fid iid]
  (db/get-inspection fid iid))
