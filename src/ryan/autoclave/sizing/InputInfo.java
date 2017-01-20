package ryan.autoclave.sizing;

import android.os.Parcel;
import android.os.Parcelable;

public class InputInfo implements Parcelable {

	protected double flowrate, retentionTime, freeboard, gassingFactor,
			headType, diameter;

	public InputInfo(double flowrate, double retentionTime, double freeboard,
			double gassingFactor, double headType, double diameter) {
		this.flowrate = flowrate;
		this.retentionTime = retentionTime;
		this.freeboard = freeboard;
		this.gassingFactor = gassingFactor;
		this.headType = headType;
		this.diameter = diameter;
	}

	public InputInfo(Double[] data) {
		this.flowrate = data[0];
		this.retentionTime = data[1];
		this.freeboard = data[2];
		this.gassingFactor = data[3];
		this.headType = data[4];
		this.diameter = data[5];
	}
	
	/**
	 * @return a value unique to the user inputs
	 */
	public String getUnique() {
		String unique =  String.valueOf(flowrate).concat(
				String.valueOf(retentionTime).concat(
						String.valueOf(freeboard).concat(
								String.valueOf(gassingFactor).concat(
										String.valueOf(headType).concat(
												String.valueOf(diameter))))));
		System.out.println("Unique"+unique);
		return unique;
	}

	public String getValuesAsString() {
		return ("Flowrate: " + getFlowrate() + "\nRetention time:"
				+ getRetentionTime() + "\nFreeboard: " + getFreeboard()
				+ "\nGassing Factor: " + getGassingFactor() + "\nHeadtype: "
				+ getHeadTypeAsString() + "\nDiameter: " + getDiameter());

	}

	/**
	 * @return the flowrate
	 */
	public double getFlowrate() {
		return flowrate;
	}

	/**
	 * @return the freeboard
	 */
	public double getFreeboard() {
		return freeboard;
	}

	/**
	 * @return the gassingFactor
	 */
	public double getGassingFactor() {
		return gassingFactor;
	}

	/**
	 * @return the retentionTime
	 */
	public double getRetentionTime() {
		return retentionTime;
	}

	/**
	 * @return the headType
	 */
	public double getHeadType() {
		return headType;
	}

	public String getHeadTypeAsString() {
		if (headType == 0)
			return "Hemi Spherical";
		return "2:1 Eliptical";
	}

	/**
	 * @return the diameter
	 */
	public double getDiameter() {
		return diameter;
	}

	public double get_fluid_height() {
		return (diameter * 1000 - freeboard);
	}

	public double get_Head_static_volume() {
		if (headType == 0.0) {
			return Math.PI / 6
					* (1.5 * diameter - ((diameter * 1000 - freeboard) / 1000))
					* (Math.pow(((diameter * 1000 - freeboard) / 1000), 2));
		} else {
			return Math.PI * Math.pow(diameter, 3) / 24;
		}
	}

	public double get_dynamic_flow() {
		return (flowrate * retentionTime / 60);
	}

	public double total_static_volume() {
		return (flowrate * retentionTime / 60) / gassingFactor;
	}

	public double getHeadVolume() {
		if (headType == 0) {
			return Math.PI * (Math.pow(diameter, 3)) / 6;
		} else {
			return Math.PI * Math.pow(diameter, 3) / 12;
		}
	}

	public double getCylinderVolume() {
		return Math.PI * Math.pow(diameter, 2) / 4 * getLength();
	}

	public double getVolume() {
		return getHeadVolume() + Math.PI * Math.pow(diameter, 2) / 4
				* getLength();
	}

	public double gethsVolume() {
		return (flowrate * (retentionTime / 60)) / gassingFactor;
	}

	public double getDyVolume() {
		return (flowrate * (retentionTime / 60));
	}

	public double getFluidHeight() {
		return diameter * 1000 - freeboard;
	}

	public double getLength() {
		double length = (((total_static_volume() - (2 * get_Head_static_volume()))
				/ ((0.25 * Math.PI * Math.pow((diameter * 1000), 2)) - (Math
						.pow((diameter * 500), 2)
						* Math.acos(((diameter * 1000) / 2 - (freeboard))
								/ ((diameter * 1000) / 2)) - (((diameter * 1000) / 2) - (freeboard))
						* Math.sqrt((diameter * 1000) * (freeboard)
								- (freeboard * freeboard)))) * (Math.pow(10, 9))) / 1000);
		return length;
	}

	public double get_compart_num() {
		return Math.floor((getLength() / diameter)) + 1;
	}

	public double getLengthDiaRatio() {
		double length_dia_Ratio = getLength() / diameter;
		return length_dia_Ratio;
	}

	// Parcelling parts
	public InputInfo(Parcel in) {
		double[] data = new double[6];

		in.readDoubleArray(data);
		this.flowrate = data[0];
		this.retentionTime = data[1];
		this.freeboard = data[2];
		this.gassingFactor = data[3];
		this.headType = data[4];
		this.diameter = data[5];
	}

	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeDoubleArray(new double[] { flowrate, retentionTime,
				freeboard, gassingFactor, headType, diameter });

	}

	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
		public InputInfo createFromParcel(Parcel in) {
			return new InputInfo(in);
		}

		public InputInfo[] newArray(int size) {
			return new InputInfo[size];
		}
	};

}
