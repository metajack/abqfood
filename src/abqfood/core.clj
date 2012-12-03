(ns abqfood.core
  (:require [compojure.core :refer [defroutes context GET]]
            [compojure.route :as route]
            [compojure.handler :as handler]
            [ring.middleware.reload :as reload]
            [ring.middleware.stacktrace :as st]
            [clojure.java.io :as io]
            [clojure.data.json :as json]
            [abqfood.views :as views]))


(defn- json-response
  "Convert data to JSON response."
  [data]
  {:status 200
   :headers {"content-type" "application/json"}
   :body (json/write-str data)})

(defroutes api-routes
  (GET "/facilities" []
       #(-> % views/facilities json-response))
  (GET "/facility/:fid" [fid :as req]
       (fn [_] (-> req (views/inspections-by-facility fid) json-response)))
  (GET "/facility/:fid/inspection/:iid" [fid iid :as req]
       (fn [_] (-> req (views/inspection fid iid) json-response)))
  (GET "/results" []
       #(-> % views/results json-response))
  (GET "/inspections" []
       #(-> % views/inspections-by-date json-response))
  (GET "/inspections/:rid" [rid :as req]
       (fn [_] (-> req (views/inspections-by-result rid) json-response))))

(defroutes app-routes
  (context "/api" [] (handler/api api-routes))
  (GET "/" [] (slurp (io/resource "public/index.html")))
  (route/resources "/" {:root "public"})
  (route/not-found "not found"))

(def app
  (-> app-routes
      reload/wrap-reload
      st/wrap-stacktrace))
