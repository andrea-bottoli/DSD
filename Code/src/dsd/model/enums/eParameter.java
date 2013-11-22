package dsd.model.enums;

import java.util.HashMap;
import java.util.Map;

public enum eParameter
{
	/*
	 * ##########################################################
	 * ##########################################################
	 * #####												#####
	 * #####		  GEOMETRY OF THE STACK N.30			#####
	 * #####												#####
	 * ##########################################################
	 * ##########################################################
	 */
	
	DiameterOfThePylon("Diameter of the pylon"), 
	DistanceBetweenTwoLineOfPylon("Distance between two line of pylon"),
	HeightOfTheLowerBeam("Height of the lower beam"),
	HeightOfTheReferenceOfTheBottomOfTheRiver("Height of the reference of the bottom of the river"),
	DistanceBetweenThePulvinoAndTheInferiorBeam("Distance between the pulvino and the inferior beam"),
	DistanceBetweenTheInferiorBeamAndTheBottom_ref("Distance between the inferior beam and the bottom_ref"),
	MeanValueOfH1("Mean value of h1"),
	SinkingOfTheJointsOverTheGround("Sinking of the joints over the ground"),
	WidthOfTheChassis("Width of the chassis"),
	
	/*
	 * ##########################################################
	 * ##########################################################
	 * #####												#####
	 * #####		  		WIND THRUST						#####
	 * #####												#####
	 * ##########################################################
	 * ##########################################################
	 */
	
	PlanimetricAnticlockwiseInclinationOfTheBridgeFormTheNorth("Planimetric anticlockwise inclination of the bridge form the north"),
	DragPlankingCoefficient("Drag planking coefficient"),
	AirDensity("Air density"),
	PlankingAreaExposedToTheWindPressure("Planking area exposed to the wind pressure"),
	SurfaceOfTrafficExposedToTheWindPressure("Surface of traffic exposed to the wind pressure"),
	CoefficientOfReductionForA1AndA2TrafficScenarios("Coefficient of reduction for A1 and A2 traffic scenarios"),
	CoefficientOfReductionForA3TrafficScenario("Coefficient of reduction for A3 traffic scenario"),
	ThrustCenterDueToLongitudinalAsymmetryOnlyOfSVplank("Thrust center due to longitudinal asymmetry, only of SVplank"),
	ArmForBendingMomentOfSVplank("arm for bending moment of SVplank"),
	ArmForBendingMomentOfSVtraf("arm for bending moment of SVtraf"),
	
	/*
	 * ##########################################################
	 * ##########################################################
	 * #####												#####
	 * #####		  	HYDRODYNAMIC THRUST					#####
	 * #####												#####
	 * ##########################################################
	 * ##########################################################
	 */
	
	DragPlankingCoefficientDequals0("Drag planking coefficient (D=0)"),
	DragPlankingCoefficientDequals1("Drag planking coefficient (D=1)"),
	WaterDensity("Water density"),
	AreaReductionForDequals1("Area reduction for D=1"),
	CoefficientAforTheRelationVwaterIDRO1("Coefficient a for the relation Vwater([IDRO1])"),
	CoefficientBforTheRelationVwaterIDRO1("Coefficient b for the relation Vwater([IDRO1])"),
	CoefficientCforTheRelationVwaterIDRO1("Coefficient c for the relation Vwater([IDRO1])"),
	HeightLimitOfTheRiverForParametersa1b1c1("Height limit of the river for parameters a1,b1,c1"),
	CoefficientA1forQhWhenIDRO1lessThanHwater1("Coefficient a1 for Q(h) when [IDRO1] < hwater1"),
	CoefficientB1forQhWhenIDRO1lessThanHwater1("Coefficient b1 for Q(h) when [IDRO1] < hwater1"),
	CoefficientC1forQhWhenIDRO1lessThanHwater1("Coefficient c1 for Q(h) when [IDRO1] < hwater1"),
	HeightLimitOfTheRiverForParametersa2b2c2("Height limit of the river for parameters a2,b2,c2"),
	CoefficientA2forQhWhenIDRO1lessThanHwater1("Coefficient a2 for Q(h) when [IDRO1] < hwater2"),
	CoefficientB2forQhWhenIDRO1lessThanHwater1("Coefficient b2 for Q(h) when [IDRO1] < hwater2"),
	CoefficientC2forQhWhenIDRO1lessThanHwater1("Coefficient c2 for Q(h) when [IDRO1] < hwater2"),
	MaxHeightLevelOfTheRiverAndLimitForUseParametersa3b3c3("Max height level of river and limit for use parameter a3,b3,c3"),
	CoefficientA3forQhWhenIDRO1lessThanHwater1("Coefficient a3 for Q(h) when hwater2 < [IDRO1] < hmax"),
	CoefficientB3forQhWhenIDRO1lessThanHwater1("Coefficient b3 for Q(h) when hwater2 < [IDRO1] < hmax"),
	CoefficientC3forQhWhenIDRO1lessThanHwater1("Coefficient c3 for Q(h) when hwater2 < [IDRO1] < hmax"),
	
	/*
	 * ##########################################################
	 * ##########################################################
	 * #####												#####
	 * #####		 	 WEIGHT OF THE STACK				#####
	 * #####												#####
	 * ##########################################################
	 * ##########################################################
	 */
	
	PlankWeightOnTheStack("Plank weight on the stack"),
	WeightOfSinglePulvino("Weight of single pulvino"),
	WeightOfTheTrunkOfPylon("Weight of the trunk of pylon"),
	WeightOfTheSingleBeam("Weight of the single beam"),
	WeightPerMeterOfPylon("Weight per meter of pylon"),
	MomentGeneratedByAsymmetry("Moment generated by asymmetry"),
	
	/*
	 * ##########################################################
	 * ##########################################################
	 * #####												#####
	 * #####		 	 SHIFTING WEIGHTS					#####
	 * #####												#####
	 * ##########################################################
	 * ##########################################################
	 */
	
	AxialLoadForLoadCombinationA1("Axial load for load combination A1"),
	BendingMomentXXForLoadCombinationA1("Bending moment xx for load combination A1"),
	BendingMomentYYForLoadCombinationA1("Bending moment yy for load combination A1"),
	AxialLoadForLoadCombinationA2("Axial load for load combination A2"),
	BendingMomentXXForLoadCombinationA2("Bending moment xx for load combination A2"),
	BendingMomentYYForLoadCombinationA2("Bending moment yy for load combination A2"),
	AxialLoadForLoadCombinationA3("Axial load for load combination A3"),
	BendingMomentXXForLoadCombinationA3("Bending moment xx for load combination A3"),
	BendingMomentYYForLoadCombinationA3("Bending moment yy for load combination A3"),
	
	/*
	 * ##########################################################
	 * ##########################################################
	 * #####												#####
	 * #####		 		 Vehicle braking				#####
	 * #####												#####
	 * ##########################################################
	 * ##########################################################
	 */
	
	ValueOfTheForceDueToTheVehicleBraking("Value of the force due to the vehicle braking"),
	ArmForTheVehicleBrakingMoment("arm for the vehicle braking moment");
	
	
	
	
	
	
	
	private String name;
	 
    /**
     * A mapping between the integer code and its corresponding Parameter to facilitate lookup by code.
     */
    private static Map<String, eParameter> NameToParameterMapping;
 
    private eParameter(String name) {
        this.name = name;
    }
 
    public static eParameter geteParameter(String name) {
        if (NameToParameterMapping == null) {
            initMapping();
        }
        return NameToParameterMapping.get(name);
    }
 
    private static void initMapping() {
    	NameToParameterMapping = new HashMap<String, eParameter>();
        for (eParameter s : values()) {
        	NameToParameterMapping.put(s.name, s);
        }
    }
 
    public String getName() {
        return name;
    }
}
