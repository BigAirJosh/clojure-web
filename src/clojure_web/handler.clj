(ns clojure-web.handler
  (:use [compojure.core]
        [korma.db]
        [korma.core]
        [ring.util.response])
  (:require [compojure.handler :as handler]
            [ring.middleware.json :as middleware]
            [clojure.string :as str]
            [compojure.route :as route]
            [clojure.tools.logging :as log])
  (:gen-class))

(def db (mysql {:host "localhost"
                :port 3306
                :db "clojuredb"
                :make-pool? true
                :user "root"
                :password "0cean11"
                :naming {
                         :keys str/lower-case
                         ;; set map keys to lower
                         :fields str/lower-case}}))

(defdb korma-db db)

(declare users)

(defentity users
  (pk :id) 
  (table :users)
  (database db)
  (entity-fields :id :name :password))

(defn get-users []
  "gets all users"
  (log/info "all users")
  (response (select users)))

(defn get-user 
  "get a user with the supplied id"
  [id]
  (println "get-user id:" id)
  (def results (select users (where {:id id})))
  (if (empty? results) 
    {:status 404}
    (response (first results))))

(defn add-user 
  "adds a user from the supplied user map {:name name :password password}"
  [user]
  (println "add-user user: " user)
  (get-user (:generated_key (insert users (values (dissoc user :id))))))

(defn update-user 
  "update the supplied user by its id (all values are updated)"
  [id user]
  (println "update-user user: " user)
  (update users
          (set-fields {:name (user :name) :password (user :password)})
          (where {:id id}))
  (get-user id))

(defn delete-user
  "deletes thes supplied user by its id"
  [id]
  (println "delet-user id: " id)
  (delete users
          (where {:id id}))
  (response {:success true}))

(defroutes app-routes
  (route/resources "/")
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
