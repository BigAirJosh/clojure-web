(ns clojure-web.views
  (:use [hiccup.core]
        [hiccup.page]))

(defn navbar [view controls] 
  [:nav.navbar.navbar-default.navbar-fixed-top {:role "navigation"}
   [:div.container-fluid
    [:div.navbar-header
     [:button.navbar-toggle {:type "button" :data-toggle "collapse" :data-target ".navbar-collapse"}
      [:span.icon-bar]
      [:span.icon-bar]
      [:span.icon-bar]]
     [:a.navbar-brand {:href "#"} "Clojure Web"]]
    [:div.collapse.navbar-collapse
     [:ul.nav.navbar-nav
      [:li (when (= view :home) {:class "active"}) [:a {:href "/home"} "Home"]]
      [:li (when (= view :users) {:class "active"}) [:a {:href "/users"} "Users"]]]
     (when (not-empty controls) 
       [:ul.navbar-nav.navbar-left
        controls])
     [:ul.nav.navbar-nav.navbar-right 
      [:li [:a.cw-nav-link {:href "/logout"} [:img.img-circle {:src "images/t.png"}] " Logout"]]
      ]]]])

(defn layout 
  ([content view] (layout content view []))
  ([content view nav]
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
    [:body 
     (navbar view nav)
     [:div.cw-content
      content]
     (include-js "js/jquery.min.js")
     (include-js "js/bootstrap.min.js")]])))

(defn home-view []
  [:div
   [:div.jumbotron 
    [:h1 "Clojure Web"]
    [:p "Clojure web is a place where clojure is doing most (if not all) the legwork."]]
   [:div "some more content.  I'm hoping this will fix my bug with the silly navbar"]])

(defn users-nav []
  [:button.btn.btn-primary.navbar-btn {:data-toggle "modal" :data-target "#cw-user-form"} "Add user"])

(defn users-view []
  [:div {:ng-controller "UserController"} 
   [:div#cw-user-form.modal.fade {:tabindex "-1" :role "dialog" :aria-labelledby "cw-user-form-title" :aria-hidden "true"}
    [:div.modal-dialog
     [:div.modal-content
      [:div.modal-header
       [:button.close {:type "button" :data-dismiss "modal" :aria-hidden "true" :ng-click "reset()"} "&times;"]
       [:h4#cw-user-form-title.modal-title "User form"]]
      [:div.modal-body
       [:form {:novalidate "" :role "form"}
        [:div.form-group
         [:label {:for "user-name"} "Username:"]
         [:input#user-name.form-control {:type "text" :ng-model "user.name" :placeholder "Enter name"}]]
        [:div.form-group
         [:label {:for "user-password"} "Password:"]
         [:input#user-password.form-control {:type "password" :ng-model "user.password" :placeholder "Enter password"}]]
        [:button.btn.btn-primary {:type "button" :data-dismiss "modal" :ng-click "save(user)"} "Save"]
        [:button.btn.btn-default.pull-right {:type "button" :data-dismiss "modal" :ng-click "reset()"} "Cancel"]]]]]]
   [:div
    [:table.table.table-hover
     [:thead
      [:tr
       [:th "id"][:th "name"][:th "password"][:th "action"]]]
     [:tbody
      [:tr {:ng-repeat "user in users" :ng-click "edit(user)" :data-toggle "modal" :data-target "#cw-user-form"}
       [:td "{{user.id}}"]
       [:td "{{user.name}}"]
       [:td "{{user.password}}"]
       [:td
        [:button.btn.btn-default {:type "button" :ng-click "delete(user)"} "Delete"]]]]]]
   ])
