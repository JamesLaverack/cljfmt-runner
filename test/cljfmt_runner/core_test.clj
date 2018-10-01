(ns cljfmt-runner.core-test
  (:require [clojure.test :refer :all]
            [cljfmt-runner.core :as rcore]))

(def example-root "test-resources/example-project/")

(deftest io-test
  (testing "Test that a directory can be detected"
    (is (rcore/directory? (clojure.java.io/file example-root))))
  (testing "Test that a file can be detected"
    (is (rcore/file? (clojure.java.io/file (str example-root "deps.edn")))))
  (testing "Test that a Clojure source file can be detected"
    (is (rcore/clojure-source? (clojure.java.io/file (str example-root "src/example/hello.clj"))))))
