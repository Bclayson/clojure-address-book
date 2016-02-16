(defproject address-book "0.1.0-SNAPSHOT"
  :description "An address book to learn compojure"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [compojure "1.4.0"]
                 [instaparse "1.4.1"]
                 [ring/ring-defaults "0.1.5"]
                 [ring/ring-json "0.3.1"]
                 [hiccup "1.0.5"]
                 [org.clojure/java.jdbc "0.3.6"]
                 [postgresql/postgresql "9.3-1102.jdbc41"]
                 [yesql "0.5.0-rc1"]
                 [environ "1.0.2"]]
  :plugins [[lein-ring "0.9.7"]
            [lein-environ "1.0.2"]]
  :ring {:init          address-book.handler/init
         :handler       address-book.handler/app
         :auto-refresh? true}
  :profiles {:test-local    {:dependencies [[midje "1.6.3"]
                                            [javax.servlet/servlet-api "2.5"]
                                            [ring-mock "0.1.5"]]

                             :plugins      [[lein-midje "3.1.3"]]}

             ;; Set these in ./profiles.clj
             :test-env-vars {}
             :dev-env-vars  {}

             :test          [:test-local :test-env-vars]
             :dev           [:dev-env-vars]})
