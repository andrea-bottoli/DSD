package dsd.calculations;

import java.awt.geom.Point2D.Double;
import java.util.ArrayList;
import java.util.List;

public class AlgebraicFunctions {

	/*
	 * ##########################################################
	 * ##########################################################
	 * #####												#####
	 * #####				Algebraic Formulas				#####
	 * #####												#####
	 * ##########################################################
	 * ##########################################################
	 */	
	/**
	 * This method calculates the roots of a polynomial function of degree 1
	 * in the following canonical form:
	 * 
	 * 			a*y + b*x + c = 0
	 * 
	 * 			y = (-b/a)*x + (-c/a)
	 * 
	 * 			m = -b/a       q = -c/a
	 * 
	 * 		=>  y = m*x + q
	 * 			
	 * 			the root is for y=0, so
	 *
	 * 			m*x + q = 0
	 * 
	 * 		=>	x = -q/m	
	 * 
	 * @param a the coefficient of the delta x
	 * @param b the coefficient of the delta y
	 * @param c the known term
	 * @return the list of three roots of the 1rd degree function
	 */
	public static Double rootsOfCanonical1rdDegreeFunction(double a, double b, double c)
	{	
		if(a == 0){
			return rootsOf1rdDegreeFunction(b, c);
		}else{
			return rootsOf1rdDegreeFunction((-b/a),(-c/a));
		}
	}
	
	/**
	 * This method calculates the roots of a polynomial function of degree 1
	 * in the following form:
	 * 
	 * 			y = mx + q
	 * 
	 * The root is for y=0, so
	 * 
	 * 			mx + q = 0
	 * 
	 * 		=> 	x = -q/m
	 * 
	 * @param m the coefficient of the term of degree 1
	 * @param q the coefficient of the term of degree 0
	 * @return the list of three roots of the 1rd degree function
	 */
	public static Double rootsOf1rdDegreeFunction(double m, double q)
	{
		Double root = new Double();
		
		/*
		 * The function is
		 * 
		 * 		y = mx + q
		 * 
		 * The root is for y=0, so
		 * 
		 * 		mx + q = 0
		 * 
		 * 	=> x = -q/m
		 */
		if(m != 0){
			root.setLocation((-q/m), 0);
		}else{
			throw new ArithmeticException("m = 0");
		}
		
		
		return root;
	}
	
	
	/**
	 * This method calculates the roots of a polynomial function of degree 2
	 *  in the following form:
	 * 
	 * 			a*x^2 + b*x^1 + c = 0
	 *  
	 * @param a the coefficient of the term of degree 2
	 * @param b the coefficient of the term of degree 1
	 * @param c the coefficient of the term of degree 0
	 * @return the list of three roots of the 2rd degree function
	 */
	public static List<Double> rootsOf2rdDegreeFunction(double a, double b, double c)
	{
		ArrayList<Double> rootsList = new ArrayList<Double>();
		Double r = new Double();
		double determ;
		
		if(a == 0)
		{
			//The equation is a linear function
			rootsList.add(rootsOf1rdDegreeFunction(b, c));
		}else if(b == 0){
			/*
			 * The equation is in the form of
			 * 
			 * 		a*x^2 + c = 0
			 * 
			 * so the solutions are
			 * 
			 * 		x1 = + sqrt(-c/a)
			 * 		x2 = - sqrt(-c/a)
			 */
			determ = -(c/a);
			
			if(determ >= 0){
				/*
				 * Real solutions
				 */
				r.setLocation(+Math.sqrt(determ), 0);
				rootsList.add(r);
				
				r.setLocation(-Math.sqrt(determ), 0);
				rootsList.add(r);
			}else{
				/*
				 * complex solutions
				 */
				r.setLocation(0, +Math.sqrt(-determ));
				rootsList.add(r);
				
				r.setLocation(0, -Math.sqrt(-determ));
				rootsList.add(r);
			}			
		}else if(c == 0)
		{
			/*
			 * The equation is in the form of
			 * 
			 * 		a*x^2 + b*x = 0
			 * 
			 * regrouping by x we obtain
			 * 
			 * 		x*(a*x +b) = 0
			 * 
			 * so a root is x=0 and the other one 
			 * is obtained solving the linear function 
			 * in the brackets
			 * 
			 * 		x1 = 0
			 * 		x2 = -b/a 
			 */
			rootsList.add(new Double(0, 0));
			rootsList.add(rootsOf1rdDegreeFunction(a,b));
		}else
		{
			/*
			 * The equation is in the form of
			 * 
			 * 		a*x^2 + b*x^ + c = 0
			 * 
			 * so the solutions are
			 * 
			 * 		x1 = (-b + sqrt(b^2 -4*c*a))/(2*a)
			 * 		x2 = (-b - sqrt(b^2 -4*c*a))/(2*a)
			 */
			determ = Math.pow(b, 2) - 4*a*c;
			
			if(determ >= 0){
				/*
				 * Real solutions
				 */
				r.setLocation((-b + Math.sqrt(determ))/(2*a), 0);
				rootsList.add(r);
				
				r.setLocation((-b - Math.sqrt(determ))/(2*a), 0);
				rootsList.add(r);
			}else{
				/*
				 * complex solutions
				 */
				r.setLocation((-b/(2*a)), Math.sqrt(-determ)/(2*a));
				rootsList.add(r);
				
				r.setLocation((-b/(2*a)), -Math.sqrt(-determ)/(2*a));
				rootsList.add(r);
			}
		}
		
		return rootsList;
	}
	
	
	/**
	 * This method calculates the roots of a polynomial function of degree 3
	 *  in the following form:
	 * 
	 * 			a*x^3 + b*x^2 + c*x^1 + d = 0
	 * 
	 * 	This function uses the Cardano-Tartaglia method, with the substitution of François Viète
	 * 
	 * @param a the coefficient of the term of degree 3
	 * @param b the coefficient of the term of degree 2
	 * @param c the coefficient of the term of degree 1
	 * @param d the coefficient of the term of degree 0
	 * @return the list of three roots of the 3rd degree function
	 */
	public static List<Double> rootsOf3rdDegreeFunction(double a, double b, double c, double d)
	{
		ArrayList<Double> rootsList = new ArrayList<Double>();
		double determ, term1, term2;
		double p,q,u,v;
		double mod, teta;
		
		if(a == 0)
		{
			/*
			 * It's a second degree functions
			 */
			return rootsOf2rdDegreeFunction(b, c, d);
		}else if(d == 0)
		{
			/*
			 * The funcion is in the form
			 * 
			 * 		a*x^3 + b*x^2 + c*x^1 = 0
			 * 
			 * regroup x and obtain
			 * 
			 * 		x*(a*x^2 + b*x + c) = 0
			 * 
			 * so one root is x=0, and the others one are obtained 
			 * by solving the second degree function in the brackets
			 */
			rootsList.add(new Double(0, 0));
			rootsList.addAll(rootsOf2rdDegreeFunction(a,b,c));
		}else{
			
			b /= a;
			c /= a;
			d /= a;
			
			/*
			 * p = ((3*c - b^2)/3)/3
			 */
			p = (3.0*c - Math.pow(b, 2));
			p /= 9.0;
			
			/*
			 * q = ((27*d -9*b*c + 2*b^3)/27)/2
			 */
			q = 27.0*d - b*(9.0*c - 2*Math.pow(b, 2));
			q /= 54.0;
			
			/*
			 * determ = q^2 + p^3
			 */
			determ = Math.pow(q, 2) + Math.pow(p, 3);
			
			/*
			 * Term that represent b/(3*a) due to the
			 * substitution of François Viète
			 */
			term1 = b/3.0;
			
			if(determ > 0)
			{
				/*
				 * One root is real, two are complex
				 */
				
				/*
				 * u = (-q + sqrt(determ))^(1/3)
				 */
				u = -q + Math.sqrt(determ);
				
				if(u>=0)
				{
					u = Math.pow(u, 1.0/3.0);
				}else{
					u = -Math.pow(-u, 1.0/3.0);
				}
				
				/*
				 * v = (-q - sqrt(determ))^(1/3)
				 */
				v = -q - Math.sqrt(determ);
				
				if(v>=0)
				{
					v = Math.pow(v, 1.0/3.0);
				}else{
					v = -Math.pow(-v, 1.0/3.0);
				}
				
				/*
				 * FIRST SOLUTION IS
				 * 
				 * 	x1 = u + v -term1
				 */
				rootsList.add(new Double(u+v-term1,0));
				
				/*
				 * SECOND and THIRD SOLUTIONS ARE
				 * 	
				 * 	x2 = -(u+v)/2 + i(u-v)*cubeRoot(3)/2 -term1
				 * 
				 * 	x3 = -(u+v)/2 + i(-u+v)*cubeRoot(3)/2 -term1
				 */
				term1 += (u+v)/2.0;
				
				term2 = (u-v)*Math.sqrt(3)/2.0;
				rootsList.add(new Double(-term1, term2));
				
				term2 = (-u+v)*Math.sqrt(3)/2.0;
				rootsList.add(new Double(-term1, term2));
				
			}else if(determ < 0)
			{
				/*
				 * All the three solutions are real solutions and all unequal
				 */
				
				/*
				 * if in here  p is < 0
				 * 
				 *  and we have to convert the complex number from the form
				 *  
				 *  	-q +i*sqrt(-determ)
				 *  
				 *  to the trigonometric form
				 *  
				 *  	rho*(cos(teta) + i*sin(teta))
				 * 
				 *  	teta = arccos(x/rho)
				 *  		 = arccos(-q / sqrt(-p^3))
				 */
				mod = 2.0*Math.sqrt(-p);
				term2 = Math.pow(-p, 3);
				teta = Math.acos(-q/Math.sqrt(term2));
				
				/*
				 * solutions
				 */
				rootsList.add(new Double(mod*Math.cos(teta/3.0) - term1, 0));
				rootsList.add(new Double(mod*Math.cos((teta + 2.0*Math.PI)/3.0) - term1, 0));
				rootsList.add(new Double(mod*Math.cos((teta + 4.0*Math.PI)/3.0) - term1, 0));
			}else{
				/*
				 * Determ == 0
				 */
				
				/*
				 * All the three solutions are real solutions
				 * and are:
				 * 
				 *  x1 = -2*cubeRoot(q) - term1
				 *  
				 *  x2 = x3 = cubeRoot(q)
				 */
				if(q >= 0){
					term2 = Math.pow(q, 1.0/3.0);
				}else{
					term2 = -Math.pow(-q, 1.0/3.0);
				}
				
				rootsList.add(new Double(-2*term2 - term1, 0));
				rootsList.add(new Double(term2 - term1, 0));
				rootsList.add(new Double(term2 - term1, 0));
			}
		}
		
		return rootsList;
	}
	
	
	
	/*
	 * EVALUATION OF A FUNCTION IN A GIVEN INPUT POINT
	 */	
	/**
	 * @param a coefficient of term of degree 3
	 * @param b coefficient of term of degree 2
	 * @param c coefficient of term of degree 1
	 * @param d known term
	 * @param x value of x to be evaluated
	 * @return the value of the function for the x value
	 */
	public static double get3rdDegreePolynomialFunctionValue(double a, double b, double c, double d, double x)
	{
		if(a == 0){
			/*
			 * is a 2nd degree polynomial function
			 */
			return get2ndDegreePolynomialFunctionValue(b,c,d,x);
		}
		
		return (a*Math.pow(x, 3) + b*Math.pow(x, 2) + c*x + d);
	}
	
	
	
	/**
	 * @param a coefficient of term of degree 2
	 * @param b coefficient of term of degree 1
	 * @param c known term
	 * @param x value of x to be evaluated
	 * @return the value of the function for the x value
	 */
	public static double get2ndDegreePolynomialFunctionValue(double a, double b, double c, double x)
	{
		if(a == 0){
			/*
			 * is a 1st degree polynomial function
			 */
			return get1stDegreePolynomialFunctionValue(b,c,x);
		}
		
		return (a*Math.pow(x, 2) + b*x + c);
	}
	
	
	/**
	 * @param m coefficient of term of degree 1
	 * @param q known term
	 * @param x value of x to be evaluated
	 * @return the value of the function for the x value
	 */
	public static double get1stDegreePolynomialFunctionValue(double m, double q, double x)
	{
		return (m*x + q);
	}
	
	
	/**
	 * @param p1 first point
	 * @param p2 second point
	 * @return the distance between the two points in one dimension
	 */
	public static double getDistanceBetweenTwo1DPoints(double p1, double p2){
		return (Math.abs(p1-p2));
	}
	
	
	/**
	 * @param p1 first point
	 * @param p2 second point
	 * @return the distance between the two points in two dimensions
	 */
	public static double getDistanceBetweenTwo2DPoints(Double p1, Double p2){
		return (Math.sqrt(Math.pow(getDistanceBetweenTwo1DPoints(p1.getX(),p2.getX()), 2) + Math.pow(getDistanceBetweenTwo1DPoints(p1.getY(),p2.getY()), 2)));
	}
}
