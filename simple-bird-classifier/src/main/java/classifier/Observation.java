package classifier;

public class Observation {
	
	private String samplingID;
	private String state = "?";
	private String country = "?";
	private String county = "?";
	private String time = "?";
	private String weekOfYear = "?";
	private String causPrec = "?";
	private String causSnow = "?";
	private String causTempAvg = "?";
	private String elevGT = "?";
	private String elevNed = "?";
	private String housingDensity = "?";
	private String housingVacant = "?";
	private String isBirdPresent = "?";
	private String region = "?";
	private String distFromFlowingFresh = "?";
	private String distInFlowingFresh = "?";
	private String distFromStandingFresh = "?";
	private String distInStandingFresh = "?";
	private String distFromWetVegFresh = "?";
	private String distInWetVegFresh = "?";
	private String distFromFlowingBlackish = "?";
	private String distInFlowingBlackish = "?";
	private String distFromStandingBlackish = "?";
	private String distInStandingBlackish = "?";
	private String distFromWetVegBlackish = "?";
	private String distInWetVegBlackish = "?";
	



	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.isBirdPresent+" ");
		if(!this.time.equalsIgnoreCase("?"))
			sb.append(1+":"+this.time+" ");
		if(!this.weekOfYear.equalsIgnoreCase("?"))
			sb.append(2+":"+this.weekOfYear+" ");
		if(!this.causPrec.equalsIgnoreCase("?"))
			sb.append(3+":"+this.causPrec+" ");
		if(!this.causSnow.equalsIgnoreCase("?"))
			sb.append(4+":"+this.causSnow+" ");
		if(!this.causTempAvg.equalsIgnoreCase("?"))
			sb.append(5+":"+this.causTempAvg+" ");
		if(!this.elevGT.equalsIgnoreCase("?"))
			sb.append(6+":"+this.elevGT+" ");
		if(!this.elevNed.equalsIgnoreCase("?"))
			sb.append(7+":"+this.elevNed+" ");
		if(!this.housingDensity.equalsIgnoreCase("?"))
			sb.append(8+":"+this.housingDensity+" ");
		if(!this.housingVacant.equalsIgnoreCase("?"))
			sb.append(9+":"+this.housingVacant+" ");
		if(!this.region.equalsIgnoreCase("?"))
			sb.append(10+":"+this.region+" ");
		if(!this.distFromFlowingFresh.equalsIgnoreCase("?"))
			sb.append(11+":"+this.distFromFlowingFresh+" ");
		if(!this.distInFlowingFresh.equalsIgnoreCase("?"))
			sb.append(12+":"+this.distInFlowingFresh+" ");
		if(!this.distFromStandingFresh.equalsIgnoreCase("?"))
			sb.append(13+":"+this.distFromStandingFresh+" ");
		if(!this.distInStandingFresh.equalsIgnoreCase("?"))
			sb.append(14+":"+this.distInStandingFresh+" ");
		if(!this.distFromWetVegFresh.equalsIgnoreCase("?"))
			sb.append(15+":"+this.distFromWetVegFresh+" ");
		if(!this.distInWetVegFresh.equalsIgnoreCase("?"))
			sb.append(16+":"+this.distInWetVegFresh+" ");
		if(!this.distFromFlowingBlackish.equalsIgnoreCase("?"))
			sb.append(17+":"+this.distFromFlowingBlackish+" ");
		if(!this.distInFlowingBlackish.equalsIgnoreCase("?"))
			sb.append(18+":"+this.distInFlowingBlackish+" ");
		if(!this.distFromStandingBlackish.equalsIgnoreCase("?"))
			sb.append(19+":"+this.distFromStandingBlackish+" ");
		if(!this.distInStandingBlackish.equalsIgnoreCase("?"))
			sb.append(20+":"+this.distInStandingBlackish+" ");
		if(!this.distFromWetVegBlackish.equalsIgnoreCase("?"))
			sb.append(21+":"+this.distFromWetVegBlackish+" ");
		if(!this.distInWetVegBlackish.equalsIgnoreCase("?"))
			sb.append(22+":"+this.distInWetVegBlackish);
		

		return sb.toString();
	}

	public Observation() {}

	public Observation(String[] dataRecord) {
		
		this.samplingID = dataRecord[DataIndex.samplingID];
		this.state = dataRecord[DataIndex.state];
		this.country = dataRecord[DataIndex.country];
		this.county = dataRecord[DataIndex.county];
		//this.time = dataRecord[DataIndex.time];
		this.time = getTimeCategory(dataRecord[DataIndex.time]);
		//this.weekOfYear = String.valueOf((int)Integer.parseInt(dataRecord[DataIndex.day]) / 7);
		this.causPrec = dataRecord[DataIndex.causPrec];
		this.causSnow = dataRecord[DataIndex.causSnow];
		this.causTempAvg = dataRecord[DataIndex.causTempAvg];
		this.elevGT = dataRecord[DataIndex.elevGT];
		this.elevNed = dataRecord[DataIndex.elevNed];
		this.housingDensity = dataRecord[DataIndex.housingDensity];
		this.housingVacant = dataRecord[DataIndex.housingVacant];
		this.isBirdPresent = getPresentFlag(dataRecord[DataIndex.agelaiusPhoeniceus]);
		this.distFromFlowingFresh = dataRecord[DataIndex.distFromFlowingFresh];
		this.distInFlowingFresh = dataRecord[DataIndex.distInFlowingFresh];
		this.distFromStandingFresh = dataRecord[DataIndex.distFromStandingFresh];
		this.distInStandingFresh = dataRecord[DataIndex.distInStandingFresh];
		this.distFromWetVegFresh = dataRecord[DataIndex.distFromWetVegFresh];
		this.distInWetVegFresh = dataRecord[DataIndex.distInWetVegFresh];
		this.distFromFlowingBlackish = dataRecord[DataIndex.distFromFlowingBlackish];
		this.distInFlowingBlackish = dataRecord[DataIndex.distInFlowingBlackish];
		this.distFromStandingBlackish = dataRecord[DataIndex.distFromStandingBlackish];
		this.distInStandingBlackish = dataRecord[DataIndex.distInStandingBlackish];
		this.distFromWetVegBlackish = dataRecord[DataIndex.distFromWetVegBlackish];
		this.distInWetVegBlackish = dataRecord[DataIndex.distInWetVegBlackish];
	}

	private String getPresentFlag(String val) {
		if(val.equalsIgnoreCase("?")) return "";
		if(val.equalsIgnoreCase("x")) return "1";
		return Integer.parseInt(val) > 0 ? "1" : "0";
	}

	private String getTimeCategory(String time) {

		Double tym = new Double(time);	
		if(tym <= 5.0) return "0.0";
		else if(tym <= 12.0) return "1.0";
		else if(tym <= 17.0) return "2.0";
		else if(tym <= 21.0) return "3.0";
		else return "0.0";

	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}



	public String getCountry() {
		return country;
	}



	public void setCountry(String country) {
		this.country = country;
	}



	public String getCounty() {
		return county;
	}



	public void setCounty(String county) {
		this.county = county;
	}



	public String getTime() {
		return time;
	}



	public void setTime(String time) {
		this.time = time;
	}



	public String getWeekOfYear() {
		return weekOfYear;
	}



	public void setWeekOfYear(String weekOfYear) {
		this.weekOfYear = weekOfYear;
	}



	public String getCausPrec() {
		return causPrec;
	}



	public void setCausPrec(String causPrec) {
		this.causPrec = causPrec;
	}



	public String getCausSnow() {
		return causSnow;
	}



	public void setCausSnow(String causSnow) {
		this.causSnow = causSnow;
	}



	public String getCausTempAvg() {
		return causTempAvg;
	}



	public void setCausTempAvg(String causTempAvg) {
		this.causTempAvg = causTempAvg;
	}



	public String getElevGT() {
		return elevGT;
	}



	public void setElevGT(String elevGT) {
		this.elevGT = elevGT;
	}



	public String getElevNed() {
		return elevNed;
	}



	public void setElevNed(String elevNed) {
		this.elevNed = elevNed;
	}



	public String getHousingDensity() {
		return housingDensity;
	}



	public void setHousingDensity(String housingDensity) {
		this.housingDensity = housingDensity;
	}



	public String getHousingVacant() {
		return housingVacant;
	}



	public void setHousingVacant(String housingVacant) {
		this.housingVacant = housingVacant;
	}



	public String getIsBirdPresent() {
		return isBirdPresent;
	}



	public void setIsBirdPresent(String isBirdPresent) {
		this.isBirdPresent = isBirdPresent;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getDistFromFlowingFresh() {
		return distFromFlowingFresh;
	}

	public void setDistFromFlowingFresh(String distFromFlowingFresh) {
		this.distFromFlowingFresh = distFromFlowingFresh;
	}

	public String getDistInFlowingFresh() {
		return distInFlowingFresh;
	}

	public void setDistInFlowingFresh(String distInFlowingFresh) {
		this.distInFlowingFresh = distInFlowingFresh;
	}

	public String getDistFromStandingFresh() {
		return distFromStandingFresh;
	}

	public void setDistFromStandingFresh(String distFromStandingFresh) {
		this.distFromStandingFresh = distFromStandingFresh;
	}

	public String getDistInStandingFresh() {
		return distInStandingFresh;
	}

	public void setDistInStandingFresh(String distInStandingFresh) {
		this.distInStandingFresh = distInStandingFresh;
	}

	public String getDistFromWetVegFresh() {
		return distFromWetVegFresh;
	}

	public void setDistFromWetVegFresh(String distFromWetVegFresh) {
		this.distFromWetVegFresh = distFromWetVegFresh;
	}

	public String getDistInWetVegFresh() {
		return distInWetVegFresh;
	}

	public void setDistInWetVegFresh(String distInWetVegFresh) {
		this.distInWetVegFresh = distInWetVegFresh;
	}

	public String getDistFromFlowingBlackish() {
		return distFromFlowingBlackish;
	}

	public void setDistFromFlowingBlackish(String distFromFlowingBlackish) {
		this.distFromFlowingBlackish = distFromFlowingBlackish;
	}

	public String getDistInFlowingBlackish() {
		return distInFlowingBlackish;
	}

	public void setDistInFlowingBlackish(String distInFlowingBlackish) {
		this.distInFlowingBlackish = distInFlowingBlackish;
	}

	public String getDistFromStandingBlackish() {
		return distFromStandingBlackish;
	}

	public void setDistFromStandingBlackish(String distFromStandingBlackish) {
		this.distFromStandingBlackish = distFromStandingBlackish;
	}

	public String getDistInStandingBlackish() {
		return distInStandingBlackish;
	}

	public void setDistInStandingBlackish(String distInStandingBlackish) {
		this.distInStandingBlackish = distInStandingBlackish;
	}

	public String getDistFromWetVegBlackish() {
		return distFromWetVegBlackish;
	}

	public void setDistFromWetVegBlackish(String distFromWetVegBlackish) {
		this.distFromWetVegBlackish = distFromWetVegBlackish;
	}

	public String getDistInWetVegBlackish() {
		return distInWetVegBlackish;
	}

	public void setDistInWetVegBlackish(String distInWetVegBlackish) {
		this.distInWetVegBlackish = distInWetVegBlackish;
	}

	public String getSamplingID() {
		return samplingID;
	}

	public void setSamplingID(String samplingID) {
		this.samplingID = samplingID;
	}
	
	
	

}
