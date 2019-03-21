package io.github.etl.util

import java.util.UUID

import io.github.etl.constant.CommonConstant._
import io.github.etl.constant.StatusCode._
import io.github.etl.domain.ResponseHeader
import io.github.etl.exception.EtlServiceException

object CommonUtility {

  def buildResponseHeader(requestId: String): ResponseHeader = {
    ResponseHeader(requestId, UUID.randomUUID().toString, CODE_2000, SUCCESS)
  }

  def buildResponseHeader(requestId: String, ex: EtlServiceException): ResponseHeader = {
    ResponseHeader(requestId, UUID.randomUUID().toString, ex.code, ex.getMessage)
  }

}
