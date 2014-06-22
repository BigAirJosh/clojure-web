(ns clojure-web.views
  (:use [hiccup.core]
        [hiccup.page]))

(defn layout [content]
  (html
   (doctype :html5)
   [:html {:lang "en" :ng-app "clojureWebApp"}
    [:head
     [:meta {:charset "utf-8"}]
     [:meta {:http-equiv "X-UA-Compatible" :content "IE=edge"}]
     [:meta {:name "viewport" :content "width=device-width, initial-scale=1"}]
     [:title "Clojure Web"]
     (include-css "css/bootstrap.css")
     (include-css "css/clojureweb.css")
     (include-js "js/angular.min.js")
     (include-js "js/controller.js")]
    [:body {:ng-controller "UserController" :style "margin: 10px;"}
     [:img.cw-img-header.img-circle {:src "images/t.png"}]
     [:h1 "Clojure Web"]
     [:p "Welcome to clojure web."]
     content]]))

  (defn home-view []
    
    [:div [:div
     [:form {:novalidate "" :role "form"}
      [:div.form-group
       [:label {:for "user-name"} "Username:"]
       [:input#user-name.form-control {:type "text" :ng-model "user.name" :placeholder "Enter name"}]]
      [:div.form-group
       [:label {:for "user-password"} "Password:"]
       [:input#user-password.form-control {:type "password" :ng-model "user.password" :placeholder "Enter password"}]]
      [:button.btn.btn-default {:type "button" :ng-click "reset()"} "Reset"]
      [:button.btn.btn-primary {:type "button" :ng-click "save(user)"} "Save"]]]
     [:div
      [:table.table.table-hover
       [:thead
        [:tr
         [:th "id"][:th "name"][:th "password"][:th "action"]]]
       [:tbody
        [:tr {:ng-repeat "user in users" :ng-click "edit(user)"}
         [:td "{{user.id}}"]
         [:td "{{user.name}}"]
         [:td "{{user.password}}"]
         [:td
          [:button.btn.btn-default {:type "button" :ng-click "delete(user)"} "Delete"]]]]]]
     (include-js "js/jquery.min.js")
     (include-js "js/bootstrap.min.js")

])
