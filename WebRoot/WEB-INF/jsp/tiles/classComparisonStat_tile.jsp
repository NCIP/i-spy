<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>


<fieldset class="gray">
<legend class="red">Select Statistic

<app:help help="Leave Default as is or select Advanced by click�ing the pertinent radio button. If you choose Advanced, click the + to access the fol�lowing options: 1.Statistical Method: Choose a test (t-test:two sample test or Wilcoxin Test: Mann-Whitney Test) from the drop-down list. 2. Multiple Comparison Adjustment: Choose an adjustment (Family-wise Error Rate (FWER): Bonferroni or False Discovery Rate (FDR): Benjamini-Hochberg) from the drop-down list. 3.Select Constraint: Select the Fold Change (2, 3, 4, 5, 6, 7, 8, 9 or 10) from the drop-down list or enter another fold change into the adjacent text box. Enter the p-value into the text box." />
</legend><br /><br />
<input type="radio" class="radio" name="statistic" value="default" checked />Default<br /><br />

<input type="radio" class="radio" name="statistic" value="advanced" />Advanced
&nbsp;&nbsp;<a href='#' id="pm" class="exp" onclick="javascript:toggleSDiv('advStatistic','pm');return false;">&nbsp;+&nbsp;</a>


<div id="advStatistic" class="divHide">

<fieldset class="gray">
<legend class="red">a)</legend>
 Statistical Method
	<html:select property="statisticalMethod">
		<html:optionsCollection property="statisticalMethodCollection"/>
	</html:select><br /><br />
	
Multiple Comparison Adjustment
	<html:select property="comparisonAdjustment">
		
		<html:optionsCollection property="comparisonAdjustmentCollection"/>
	</html:select> <br /><br />
	</fieldset>
	
	<fieldset class="gray">
<legend class="red">b) Select Constraint
<a href="javascript:void(0);" onmouseover="return overlib('Future implementation', CAPTION, 'Help');" onmouseout="return nd();">[?]</a>
</legend>

	<html:radio property="foldChange" value="list" styleClass="radio"/>
	&nbsp;&nbsp;Fold Change&nbsp;&ge;
	<html:select property="foldChangeAuto">
		<html:option value="0">&nbsp;</html:option>		
		<html:optionsCollection property="foldChangeAutoList" />
	</html:select>
	
	&nbsp;&nbsp;-OR-&nbsp;&nbsp;
	
	<html:radio property="foldChange" value="specify" styleClass="radio"/>
	&nbsp;&nbsp;Fold Change&nbsp;&ge;	
	<html:text property="foldChangeManual" size ="14" disabled="false" />
	
	<br /><br />
	
	<span id="pfill">
	&nbsp;p-Value&nbsp;&le;
		<html:text property="statisticalSignificance" size="10" />
	</span>
	
</fieldset> 
</fieldset>  