(ns cljfmt-runner.core
  (:require [cljfmt.core :as cljfmt]
            [clojure.tools.cli :refer [parse-opts]]
            [cljfmt-runner.diff :as diff]
            [clojure.java.io :as io]))

(defn file?
  "Determine if the given java.io.File is a file (as opposed to a directory)."
  [file]
  (.isFile file))

(defn directory?
  "Determine if the given java.io.File is a directory."
  [file]
  (.isDirectory file))

(defn clojure-source?
  "Determine if a the given file is a Clojure or Clojurescript source file."
  [file]
  (let [name (.getName file)]
    (or (.endsWith name ".clj")
        (.endsWith name ".cljs"))))

(defn discover
  "Discover all Clojure source files in a given directory"
  [dir]
  (->> dir
       io/file
       file-seq
       (filter file?)
       (filter clojure-source?)))

(defn check-file
  "Check a single file for formatting."
  [file]
  (let [original (slurp file)
        formatted (cljfmt/reformat-string original)
        result {:file file
                :correct? (= original formatted)
                :formatted formatted}]
    (if (:correct? result)
      result
      (assoc result :diff (diff/unified-diff (.getPath file) original formatted)))))

(defn check-all
  "Check all files under the given directories"
  [dirs]
  (->> dirs
       (mapcat discover)
       (map check-file)))

(def cli-options
  [["-d" "--dir DIR" "Include a directory to scan. Defaults to ['src' 'test']."
    :parse-fn str
    :assoc-fn (fn [m k v] (update-in m [k] #(conj % v)))
    :default ["src" "test"]]])

(defn parse-args
  [args]
  (let [args (parse-opts args cli-options)]
    (if (:errors args)
      (do
        (println "Error parsing arguments")
        (doseq [error (:errors args)]
          (println error))
        (System/exit 1))
      (:options args))))
