(defproject advent-of-code-2020 "0.1.0-SNAPSHOT"

  :description "TODO"

  :url "TODO"

  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :repl-options {:port 4555}

  :main advent-of-code-2020.main

  :plugins [[lein-eftest "0.5.9"]]

  :test-selectors {:quick (complement :slow)
                   :slow  :slow}
  
  :dependencies [;; core
                 [org.clojure/tools.nrepl "0.2.13"]
                 [org.clojure/clojure "1.10.1"]
                 [org.clojure/tools.trace "0.7.10"]

                 ;; Logging
                 [com.taoensso/timbre "5.1.0"]

                 ;; Spec helpers
                 [expound "0.7.2"]

                 ;; DI
                 [mount "0.1.16"]]
  
  :profiles {:dev     {:dependencies [[org.clojure/tools.namespace "1.0.0"]]
                       :source-paths ["dev"]}
             :uberjar {:aot [advent-of-code-2020.main]}})
