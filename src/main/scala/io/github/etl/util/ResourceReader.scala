package io.github.etl.util

import io.github.etl.constant.StatusCode._
import io.github.etl.constant.CommonConstant._
import io.github.etl.exception.EtlException

import scala.io.{BufferedSource, Source}
import scala.util.control.NonFatal

/**
  * Resource Reader - A utility to read text files from resource
  */
object ResourceReader {

  private val BASE_PATH = s"repository"
  private val RESOURCE_1 = s"$BASE_PATH/file1.txt"
  private val RESOURCE_2 = s"$BASE_PATH/file2.txt"
  private val RESOURCE_3 = s"$BASE_PATH/file3.txt"

  def load: Either[EtlException, List[String]] = {
    for {
      source1 <- read(RESOURCE_1)
      source2 <- read(RESOURCE_2)
      source3 <- read(RESOURCE_3)
    } yield source1 ++ source2 ++ source3
  }

  private def read(path: String): Either[EtlException, List[String]] = {
    try {
      Right(using(Source.fromResource(path))(_.getLines().toList))
    } catch {
      case NonFatal(ex) => Left(EtlException(CODE_3000, RESOURCE_ERROR, ex))
    }
  }

  def using[A](r: BufferedSource)(f: BufferedSource => A): A = {
    try {
      f(r)
    } finally {
      r.close()
    }
  }

  def words: Either[EtlException, List[String]] = {
    load.map(_.flatMap(_.split(" ")))
  }

  def lines: Either[EtlException, List[String]] = {
    load.map(value => value.filter(line => line.trim.nonEmpty))
  }

}
