(defproject clojure-web "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [compojure "1.1.6"]
                 [ring/ring-json "0.1.2"]
                 [korma"0.3.0"]
                 [org.clojure/java.jdbc "0.3.3"]
                 [mysql/mysql-connector-java "5.1.25"]
                 [hiccup "1.0.5"]
;                 [log4j/log4j "1.2.15" :exclusions [javax.mail/mail
;                                                    javax.jms/jms
;                                                    com.sun.jdmk/jmxtools
;                                                    com.sun.jmx/jmxri]]
                 [org.clojure/tools.logging "0.3.0"]]
  :plugins [[lein-ring "0.8.10"]
            [cider/cider-nrepl "0.7.0-SNAPSHOT"]]
  :ring {:handler clojure-web.handler/app}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring-mock "0.1.5"]]}})
