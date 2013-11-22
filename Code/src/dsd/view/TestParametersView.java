package dsd.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dsd.controller.ParametersController;
import dsd.model.Parameter;
import dsd.model.enums.eParameter;

public class TestParametersView extends HttpServlet
{

	// @Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
			IOException
	{
		ParametersController.IntializeCurrentParemeters();
		
		List<Parameter> parametersList = new ArrayList<Parameter>();  //ParametersController.GetCurrentValidParameters();
		parametersList.add(ParametersController.getParameter(eParameter.AirDensity));
		parametersList.add(ParametersController.getParameter(eParameter.AreaReductionForDequals1));
		parametersList.add(ParametersController.getParameter(eParameter.ArmForBendingMomentOfSVplank));
		parametersList.add(ParametersController.getParameter(eParameter.ArmForBendingMomentOfSVtraf));
		parametersList.add(ParametersController.getParameter(eParameter.ArmForTheVehicleBrakingMoment));
		parametersList.add(ParametersController.getParameter(eParameter.AxialLoadForLoadCombinationA1));
		parametersList.add(ParametersController.getParameter(eParameter.AxialLoadForLoadCombinationA2));
		parametersList.add(ParametersController.getParameter(eParameter.AxialLoadForLoadCombinationA3));
		parametersList.add(ParametersController.getParameter(eParameter.BendingMomentXXForLoadCombinationA1));
		parametersList.add(ParametersController.getParameter(eParameter.BendingMomentXXForLoadCombinationA2));
		parametersList.add(ParametersController.getParameter(eParameter.BendingMomentXXForLoadCombinationA3));
		parametersList.add(ParametersController.getParameter(eParameter.BendingMomentYYForLoadCombinationA1));
		parametersList.add(ParametersController.getParameter(eParameter.BendingMomentYYForLoadCombinationA2));
		parametersList.add(ParametersController.getParameter(eParameter.BendingMomentYYForLoadCombinationA3));
		parametersList.add(ParametersController.getParameter(eParameter.CoefficientA1forQhWhenIDRO1lessThanHwater1));
		parametersList.add(ParametersController.getParameter(eParameter.CoefficientA2forQhWhenIDRO1lessThanHwater1));
		parametersList.add(ParametersController.getParameter(eParameter.CoefficientA3forQhWhenIDRO1lessThanHwater1));
		parametersList.add(ParametersController.getParameter(eParameter.CoefficientAforTheRelationVwaterIDRO1));
		parametersList.add(ParametersController.getParameter(eParameter.CoefficientB1forQhWhenIDRO1lessThanHwater1));
		parametersList.add(ParametersController.getParameter(eParameter.CoefficientB2forQhWhenIDRO1lessThanHwater1));
		parametersList.add(ParametersController.getParameter(eParameter.CoefficientB3forQhWhenIDRO1lessThanHwater1));
		parametersList.add(ParametersController.getParameter(eParameter.CoefficientBforTheRelationVwaterIDRO1));
		parametersList.add(ParametersController.getParameter(eParameter.CoefficientC1forQhWhenIDRO1lessThanHwater1));
		parametersList.add(ParametersController.getParameter(eParameter.CoefficientC2forQhWhenIDRO1lessThanHwater1));
		parametersList.add(ParametersController.getParameter(eParameter.CoefficientC3forQhWhenIDRO1lessThanHwater1));
		parametersList.add(ParametersController.getParameter(eParameter.CoefficientCforTheRelationVwaterIDRO1));
		parametersList.add(ParametersController.getParameter(eParameter.CoefficientOfReductionForA1AndA2TrafficScenarios));
		parametersList.add(ParametersController.getParameter(eParameter.CoefficientOfReductionForA3TrafficScenario));
		parametersList.add(ParametersController.getParameter(eParameter.DiameterOfThePylon));
		parametersList.add(ParametersController.getParameter(eParameter.DistanceBetweenTheInferiorBeamAndTheBottom_ref));
		parametersList.add(ParametersController.getParameter(eParameter.DistanceBetweenThePulvinoAndTheInferiorBeam));
		parametersList.add(ParametersController.getParameter(eParameter.DistanceBetweenTwoLineOfPylon));
		parametersList.add(ParametersController.getParameter(eParameter.DragPlankingCoefficient));
		parametersList.add(ParametersController.getParameter(eParameter.DragPlankingCoefficientDequals0));
		parametersList.add(ParametersController.getParameter(eParameter.DragPlankingCoefficientDequals1));
		parametersList.add(ParametersController.getParameter(eParameter.HeightLimitOfTheRiverForParametersa1b1c1));
		parametersList.add(ParametersController.getParameter(eParameter.HeightLimitOfTheRiverForParametersa2b2c2));
		parametersList.add(ParametersController.getParameter(eParameter.HeightOfTheLowerBeam));
		parametersList.add(ParametersController.getParameter(eParameter.HeightOfTheReferenceOfTheBottomOfTheRiver));
		parametersList.add(ParametersController.getParameter(eParameter.MaxHeightLevelOfTheRiverAndLimitForUseParametersa3b3c3));
		parametersList.add(ParametersController.getParameter(eParameter.MeanValueOfH1));
		parametersList.add(ParametersController.getParameter(eParameter.MomentGeneratedByAsymmetry));
		parametersList.add(ParametersController.getParameter(eParameter.PlanimetricAnticlockwiseInclinationOfTheBridgeFormTheNorth));
		parametersList.add(ParametersController.getParameter(eParameter.PlankingAreaExposedToTheWindPressure));
		parametersList.add(ParametersController.getParameter(eParameter.PlankWeightOnTheStack));
		parametersList.add(ParametersController.getParameter(eParameter.SinkingOfTheJointsOverTheGround));
		parametersList.add(ParametersController.getParameter(eParameter.SurfaceOfTrafficExposedToTheWindPressure));
		parametersList.add(ParametersController.getParameter(eParameter.ThrustCenterDueToLongitudinalAsymmetryOnlyOfSVplank));
		parametersList.add(ParametersController.getParameter(eParameter.ValueOfTheForceDueToTheVehicleBraking));
		parametersList.add(ParametersController.getParameter(eParameter.WaterDensity));
		parametersList.add(ParametersController.getParameter(eParameter.WeightOfSinglePulvino));
		parametersList.add(ParametersController.getParameter(eParameter.WeightOfTheSingleBeam));
		parametersList.add(ParametersController.getParameter(eParameter.WeightOfTheTrunkOfPylon));
		parametersList.add(ParametersController.getParameter(eParameter.WeightPerMeterOfPylon));
		parametersList.add(ParametersController.getParameter(eParameter.WidthOfTheChassis));	
		
		Collections.sort(parametersList, new Comparator<Parameter>()
		{

			@Override
			public int compare(Parameter o1, Parameter o2)
			{
				// TODO Auto-generated method stub
				return o1.getCategory().getCode() - o2.getCategory().getCode();
			}
			
		});
		
		req.setAttribute("currentValidParameters", parametersList);
		
//		Calendar cal = Calendar.getInstance();
//		cal.set(2008, 10, 10, 10, 10, 10);
//		List<Parameter> parametersListForTimestamp = ParametersController.GetValidParametersForTimestamp(cal);
//		req.setAttribute("parametersListForTimestamp", parametersListForTimestamp);
//
//		List<Parameter> parameterHistory = ParametersController.GetParameterHistory(1);
//		req.setAttribute("parameterHistory", parameterHistory);

		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/testParametersView.jsp");
		dispatcher.forward(req, resp);
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5754463975955231994L;

}
