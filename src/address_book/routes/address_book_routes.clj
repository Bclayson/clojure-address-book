(ns address-book.routes.address-book-routes
  (:require [compojure.core :refer :all]
            [ring.util.response :as response]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [address-book.views.address-book-layout :refer :all]
            [address-book.models.query-defs :as query]))


(defn display-contact [contact contact-id]
  (if (not= (and contact-id (Integer. contact-id)) (:id contact))
    (read-contact contact)
    (edit-contact contact)))


(defn handle-request [request, callback]
  (let [params (:params request)
        headers (:headers request)
        uri (:uri request)
        method (:request-method request)]
    (callback params headers uri method)))


(defn post-route [request]
  (handle-request request (fn [params headers uri method]
                            (query/insert-contact<! params)
                            (response/redirect "/"))))


(defn get-route [request]
  (let [contact-id (get-in request [:params :contact-id])]
    (common-layout
      (for [contact (query/all-contacts)]
        (display-contact contact contact-id))
      (add-contact-form))))

(defn delete-route [request]
  (let [contact-id (get-in request [:params :contact-id])]
    (query/delete-contact<! {:id (Integer. contact-id)})
    (response/redirect "/")))

(defn update-route [request]
  (let [contact-id (get-in request [:params :id])
        name (get-in request [:params :name])
        phone (get-in request [:params :phone])
        email (get-in request [:params :email])]
    (query/update-contact<! {:name name :phone phone :email email :id (Integer. contact-id)})
    (response/redirect "/")))



(defroutes address-routes
           (GET "/" [] get-route)
           (POST "/post" [] post-route)
           (GET "/edit/:contact-id" [] get-route)
           (POST "/edit/:contact-id" [] update-route)
           (POST "/delete/:contact-id" [] delete-route))
