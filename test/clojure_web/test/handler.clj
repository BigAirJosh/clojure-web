(ns clojure-web.test.handler
  (:use clojure.test
        ring.mock.request  
        clojure-web.handler))

(deftest test-app
  (testing "main route"
    (let [response (app (request :get "/home"))]
      (is (= (:status response) 200))
      (is (.contains (:body response) "Clojure Web"))))
  
  (testing "not-found route"
    (let [response (app (request :get "/invalid"))]
      (is (= (:status response) 404)))))
