(ns cljfmt-runner.core-test
  (:require [clojure.test :refer :all]
            [cljfmt-runner.core :as rcore]))

(deftest io-test
  (testing "Test that a directory can be detected"
    (is (rcore/directory? (clojure.java.io/file "."))))
  (testing "Test that a file can be detected"
    (is (rcore/file? (clojure.java.io/file "deps.edn")))))

