(ns cljfmt-runner.check
  (:require [cljfmt.core :as cljfmt]
            [cljfmt-runner.core :refer :all]
            [cljfmt-runner.diff :as diff]))

(defn -main
  [& args]
  (let [checks (->> ["src" "test"]
                    (map discover)
                    (apply concat)
                    (map check-file))
        failed (filter #(not (:correct? %)) checks)]
    (println (str "Checked " (count checks) " file(s)."))
    (if (< 0 (count failed))
      (do (println (str (count failed) " file(s) were incorrectly formatted:"))
          (for [d (map :diff failed)]
            (println (diff/colorize-diff d)))
          (System/exit 1))
      (println "All files correctly formatted.")
      )))
