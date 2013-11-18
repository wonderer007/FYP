package components
{
	import components.LandDivision;
	
	import flash.geom.Point;

	public class Land
	{
		public function Land(idNum:Number, xBoundSize:Number, yBoundSize:Number)
		{
			id = idNum;
			numLandDiv = 0;
			xBoundarySize = xBoundSize;
			yBoundarySize = yBoundSize;
			
			
		}
		
		public function addFarmDivision(o:Point):Boolean{
		
			if(numLandDiv < 3 && checkAvailabilityOfLand(o)){
				
				landDivOrigins.push(o);
				
				++numLandDiv;
				return true;
			}
			
			return false;
		}
		
		private function checkAvailabilityOfLand(o:Point):Boolean{
			
			if(o.x + xLandDivSize > xBoundarySize || o.y + yLandDivSize > yBoundarySize || o.y - yLandDivSize < yStartCoor ){
				return false;
			}
			
			for(var i:Number = 0; i < landDivOrigins.length; i++){
				if(o.x < landDivOrigins[i].x + xLandDivSize && o.x > landDivOrigins[i].x && o.y < landDivOrigins[i].y + yLandDivSize && o.y >landDivOrigins[i].y ){
					return false;
				}
			}
			return true;
		}
		
		public function setLandDivisions(landDivs:Array):void{
			landDivisions = landDivs;
		}
		
		public function getLandDivisions():Array{
			return landDivisions;
		}
		
		public function getNumLandDivision():Number{
			return numLandDiv;
		}
		
		public function setXBoundarySize(xVal:Number):void{
			xBoundarySize = xVal;
		}
		
		public function setYBoundarySize(yVal:Number):void{
			yBoundarySize = yVal;
			yStartCoor = 0;
		}

		
		private var id:Number;
		private var numLandDiv:Number;
		private var landDivOrigins:Array = new Array();
		private var xBoundarySize:Number;
		private var yBoundarySize:Number;
		
		private var yStartCoor:Number = 79;
		
		private var xLandDivSize:Number = 301;
		private var yLandDivSize:Number = 78
		
		private var landDivisions:Array = new Array();
		
	}
}