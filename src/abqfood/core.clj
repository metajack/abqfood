(ns abqfood.core
  (:require [compojure.core :refer [defroutes context GET]]
            [compojure.route :as route]
            [compojure.handler :as handler]
            [ring.middleware.reload :as reload]
            [ring.middleware.stacktrace :as st]
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
       (fn [_] (-> req (views/inspections fid) json-response)))
  (GET "/facility/:fid/inspection/:iid" [fid iid :as req]
       (fn [_] (-> req (views/inspection fid iid) json-response))))

(defroutes app-routes
  (context "/api" [] (handler/api api-routes))
  (route/resources "/" {:root "public"})
  (route/not-found "not found"))

(def app
  (-> app-routes
      reload/wrap-reload
      st/wrap-stacktrace))
