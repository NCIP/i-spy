package gov.nih.nci.ispy.test;

import com.thoughtworks.selenium.*;
import java.util.regex.Pattern;

public class ProductionReferencedImages extends SeleneseTestCase {
	public void testProductionReferencedImages() throws Exception {
		selenium.open("/faces/referencedImages.tiles?source=ISPY&image1Label=Pre&image1TrialId=ISPY&image1PatientId=2&image1StudyInstanceUid=1.2.124.113532.192.9.54.60.20020702.141304.3659576&image1SeriesInstanceUid=1.2.840.113619.2.5.1762805546.3105.1025559471.58&image1ImageSopInstanceUid=1.2.840.113619.2.5.1762805546.3105.1025559471.276&image1dataName=Patient%20Id&image1dataValue=2&image1dataName=Baseline%20Morphology&image1dataValue=3=Area%20enhancement%20with%20irregular%20margins%20-%20with%20nodularity&image1dataName=Longest%20Diameter_PCT_CHANGE_T1-T2&image1dataValue=-10.34&image1dataName=Clinical%20Response_T1-T2&image1dataValue=3=Stable%20Disease&image2StudyInstanceUid=1.2.124.113532.192.9.54.60.20021230.122345.403213&image2SeriesInstanceUid=1.2.840.113619.2.5.1762805546.2376.1041867495.89&image2ImageSopInstanceUid=1.2.840.113619.2.5.1762805546.2376.1041867495.189&image2Label=Post&image2TrialId=ISPY&image2PatientId=2&image2dataName=Patient%20Id&image2dataValue=2&image2dataName=Baseline%20Morphology&image2dataValue=3=Area%20enhancement%20with%20irregular%20margins%20-%20with%20nodularity&image2dataName=Longest%20Diameter_PCT_CHANGE_T1-T4&image2dataValue=-44.83&image2dataName=Clinical%20Response_T1-T4&image2dataValue=3=Stable%20Disease");
		selenium.type("MAINbody:loginForm:uName", "bauerd@mail.nih.gov");
		selenium.type("MAINbody:loginForm:pass", "bauerd");
		selenium.click("MAINbody:loginForm:_id127");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=View Series");
		selenium.waitForPageToLoad("30000");
	}
}
