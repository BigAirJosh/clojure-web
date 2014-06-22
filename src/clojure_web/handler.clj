(ns clojure-web.handler
  (:use [compojure.core]
        [korma.db]
        [korma.core]
        [clojure-web.views]
        [clojure-web.users])
  (:require [compojure.handler :as handler]
            [ring.middleware.json :as middleware]
            [clojure.string :as str]
            [compojure.route :as route]))

(def db (mysql {:host "localhost"
                :port 3306
                :db "clojuredb"
                :make-pool? true
                :user "root"
                :password "developer"
                :naming {
                         :keys str/lower-case
                         :fields str/lower-case}}))

(defdb korma-db db)

(init-users db)

(defroutes app-routes
  (route/resources "/")
  (context "/users" []
    (defroutes user-routes
      (GET "/" [] (layout (home-view)))))
  (context "/api" [] 
    (context "/user" []
      (defroutes users-routes
        (GET  "/" [] (get-users))
        (POST "/" {{user :user} :params} (add-user user))
          (context "/:id" [id]
            (defroutes user-routes
              (GET "/" [] (get-user id))
              (PUT "/" {{user :user} :params} (update-user id user))
              (DELETE "/" [] (delete-user id)))))))
  (route/not-found "Not Found"))

(def app
  (-> (handler/api app-routes)
      (middleware/wrap-json-params)
      (middleware/wrap-json-response)))
