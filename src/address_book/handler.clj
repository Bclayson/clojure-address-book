(ns address-book.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ring.middleware.json :as middleware]
            [address-book.routes.address-book-routes :refer [address-routes]]
            [address-book.models.query-defs :as query]))


(defn init []
  (query/create-contacts-table-if-not-exists!))

(defroutes app-routes
           (route/not-found "Not Found")
           )

(def app
  (-> (routes address-routes app-routes)
      (wrap-defaults (assoc-in site-defaults [:security :anti-forgery] false))))
