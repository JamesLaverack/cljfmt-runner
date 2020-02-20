(ns cljfmt-runner.fix
  (:require [cljfmt-runner.core :refer :all]))

(defn -main
  [& args]
  (let [dirs (search-dirs (parse-args args) (config))
        checks (check-all dirs (config))
        failed (filter #(not (:correct? %)) checks)]
    (println (str "Checked " (count checks) " file(s)"))
    (if (< 0 (count failed))
      (do (println (str "Fixing " (count failed) " file(s)"))
          (doseq [{:keys [file formatted]} failed]
            (spit file formatted)))
      (println "All files already correctly formatted"))))
