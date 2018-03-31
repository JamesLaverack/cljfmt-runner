(ns cljfmt-runner.core-test
  (:require [clojure.test :refer :all]
            [cljfmt-runner.core :as rcore]))

(deftest directory-test
  (testing "Test that a directory can be detected"
    (is (rcore/directory? (clojure.java.io/file ".")))))
