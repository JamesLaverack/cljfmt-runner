(ns cljfmt-runner.fix)

(defn -main
  [& args]
  (let [checks (->> ["src" "test"]
                    (map discover)
                    (apply concat)
                    (map check-file)) 
        failed (filter #(not (:correct? %)) checks)]
    (println (str "Checked " (count checks) " file(s)."))
    (if (< 0 (count failed))
      (do (println (str "Fixing " (count failed) " file(s)."))
          (doseq [{:keys [f c]} failed]
            (spit f c))
          )
      (println "All files correctly formatted.")
      ))
  )
