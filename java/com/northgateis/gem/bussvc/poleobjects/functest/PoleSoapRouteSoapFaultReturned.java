package com.northgateis.gem.bussvc.poleobjects.functest;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.northgateis.gem.bussvc.framework.test.BusSvcStoryAcceptanceCriteriaReference;
import com.northgateis.pole.schema.GetEntityMetadataRequestDto;
import com.northgateis.pole.schema.GetEntityMetadataResponseDto;
import com.northgateis.pole.schema.RequestHeaderDto;

/**
 * Mingle-referenced functional tests for Story #36513. Test that a proper Soap fault is 
 * returned to the client when a message is sent to POLE directly (not using a stub route)
 * and POLE fails the message.
 * 
 */
public class PoleSoapRouteSoapFaultReturned 
	extends AbstractPoleObjectsFuncTestBase {

	@Test
	@BusSvcStoryAcceptanceCriteriaReference(
			mingleRef=36513,
			mingleTitle="SYP - Bus Svc - Framework - SOAP Faults from POLE are incorrectly returned to the client",
			acceptanceCriteriaRefs="UNKNOWN",
			given="A POLE request to get entity metadata without header information",
			when="BS sends this request to Pole directly, there is not a sub-stub route",
			then="A Soap fault should be sent back to the client")	
	public void testGetEntityMetadataFailure() throws Exception {
		
		if (jsonRestMode) {
			return;//"GetEntityMetadata not yet supported over JSON/REST"
		}
		
		GetEntityMetadataRequestDto request = new GetEntityMetadataRequestDto();
		request.addEntityType("failedEntityType");
		
		boolean isExpectedException = false;
		
		try {
			poleBusinessServices.getEntityMetadata(request);
		} catch(com.northgateis.pole.common.InvalidDataException ex) {
			logger.debug("Invalid data exception catched");
			isExpectedException = true;
		}
		
		if (!isExpectedException) {
			fail("Invalid data exception is not generated by the framework");
		}
	}

	@Test
	@BusSvcStoryAcceptanceCriteriaReference(
			mingleRef=36513,
			mingleTitle="SYP - Bus Svc - Framework - SOAP Faults from POLE are incorrectly returned to the client",
			acceptanceCriteriaRefs="UNKNOWN",
			given="A POLE request to get entity metadata with proper header",
			when="BS sends this request to Pole directly, there is not a sub-stub route",
			then="Pole response is received.")	
	public void testGetEntityMetadata() throws Exception {

		if (jsonRestMode) {
			return;//"GetEntityMetadata not yet supported over JSON/REST"
		}
		
		GetEntityMetadataRequestDto request = new GetEntityMetadataRequestDto();
		RequestHeaderDto header = new RequestHeaderDto();
		header.setPasswordHash(securityContextId);
		header.setClientName("BS");
		request.setHeader(header);
		request.addEntityType("gemCase");
		
		GetEntityMetadataResponseDto response = poleBusinessServices.getEntityMetadata(request);
		
		assertNotNull("Response from POLE", response);
	}
		
}