(defproject sofs "0.1.0-SNAPSHOT"
  :description "Handling sequences of sequences"
  :url "https://github.com/status203/sofs"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]]
  :plugins [[lein-marginalia "0.8.0"]]
  :profiles {:dev {:dependencies [[midje "1.6.3"]
                                  [criterium "0.4.3"]]}})
