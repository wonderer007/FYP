package components
{
	import flash.events.TimerEvent;
	import flash.geom.Point;
	import flash.utils.Timer;

	public class LandDivision
	{
		public function LandDivision(tOrg:Point, st:Number, frtVegType:String, sdType:String)
		{
			origin = tOrg;
			indexNumber = numberOfTiles - 1;
			state=st;
			fruitVegetableType = frtVegType;
			seedType = sdType;
			
			visitedCoordinates.push(new Point(origin.x+64,  origin.y));	//tile0
			visitedCoordinates.push(new Point(origin.x+130, origin.y-36));  //tile1
			visitedCoordinates.push(new Point(origin.x+195, origin.y-68));  //tile2
			visitedCoordinates.push(new Point(origin.x+130, origin.y+36)); //tile3
			visitedCoordinates.push(new Point(origin.x+197, origin.y+2)); //tile4
			visitedCoordinates.push(new Point(origin.x+265, origin.y-32));  //tile5
			visitedCoordinates.push(new Point(origin.x+196, origin.y+69)); //tile6
			visitedCoordinates.push(new Point(origin.x+261, origin.y+34)); //tile7
			visitedCoordinates.push(new Point(origin.x+330, origin.y)); //tile8
			
			starCoor.push(0);
			starCoor.push(1);
			starCoor.push(2);
			starCoor.push(3);
			starCoor.push(4);
			starCoor.push(5);
			starCoor.push(6);
			starCoor.push(7);
			starCoor.push(8);
			
			var i:Number =0;
			
			for(;i < numberOfTiles ; i++){
				currVisitedCoor.push(new Point(visitedCoordinates[i].x,visitedCoordinates[i].y));
			}
			
			setTimeDelay();
			t = new Timer(TIMER_INTERVAL);
			t.addEventListener(TimerEvent.TIMER, updateTimer);
			t.start();
		}
		
		public function getOrigin():Point{
			return origin;
		}
		
		public function getFruitVegetableType():String{
			return fruitVegetableType;
		}
		
		public function getSeedType():String{
			return seedType;
		}
		
		public function getIndexNumber():Number{
			return indexNumber;
		}
		
		public function getIndeces():Array{
			return starCoor;
		}

		public function getCoordinates():Array{
			return visitedCoordinates;
		}
		
		public function getState():Number{
			return state;
		}
		
		public function getPoint(): Point{
			return pt;
		}
		
		public function getTempCoor():Array{
			return currVisitedCoor;
		}
		
		public function caterChanges(currCoor:Point):Boolean{
			var tempPoint:Point = currCoor;
			
			var i:Number = indexNumber;
			var found:Boolean = false;
			
			for(; i >= 0 && !found; i--){
				
				diffX = tempPoint.x - currVisitedCoor[i].x;
				
				if( diffX < 0 ){
					diffX = diffX * -1;
				}	
				
				diffY = tempPoint.y - currVisitedCoor[i].y;
				
				if( diffY < 0 ){
					diffY = diffY * -1;
				}
				
				if(diffX < negDiffX && diffY < negDiffY){
					
					pt.x = currVisitedCoor[i].x;
					pt.y = currVisitedCoor[i].y;
					
					if(i != indexNumber){
						starCoor[i] = starCoor[indexNumber];
						currVisitedCoor[i].x = currVisitedCoor[indexNumber].x;
						currVisitedCoor[i].y = currVisitedCoor[indexNumber].y;
					}
					
					indexNumber--;
									
					found = true;
				}
			}
			
			if(indexNumber == -1){
				state = (state+1) % 4 ;
				indexNumber = numberOfTiles - 1;
				
				var j:Number =0;
				for(;j < numberOfTiles; ++j){
					//currVisitedCoor.push(visitedCoordinates[j].x,visitedCoordinates[j].y);
					starCoor[j]=j;
					currVisitedCoor[j].x=visitedCoordinates[j].x;
					currVisitedCoor[j].y=visitedCoordinates[j].y;
				}
			}
			return found;
		}
		
		/*
		code added
		*/
		
		public function setTimeDelay():void{
			timeDelay = 1000 ; 
		}
		
		public function getTimeDelay():int{
			return timeDelay;
		}
		
		public function setStartTimer():void{
			isStartTimer = false;
			setTimeDelay();
		}
		
		public function setTimerState(b:Boolean):void{
			isStartTimer = b ;
		}
		
		public function getStartTimer():Boolean{
			return isStartTimer;
		}
		
		private function updateTimer(evt:TimerEvent):void {
			
			if(timeDelay != 0){
				timeDelay -- ;
			}
			
			if(timeDelay == 0){
				isStartTimer = false;
			}
		}
		
		
		private var origin:Point;
		private var state:Number;
		private var indexNumber:Number;
		private var fruitVegetableType:String;
		private var seedType:String;
		
		private var visitedCoordinates:Array = new Array();
		private var currVisitedCoor:Array = [];
		private var pt:Point = new Point ();
		
		private var starCoor:Array = new Array();
		
		private var numberOfTiles:Number = 9;							//total number of tiles of farm in the view
		private var diffX:Number=0;
		private var diffY:Number=0;
		private var negDiffY:Number = 25;									//negligable difference on yAxis
		private var negDiffX:Number = 25;								//negligable difference on xAxis
		
		private var timeDelay:int = 50 ;
		private var isStartTimer:Boolean = true;
		private var t : Timer;
		private const TIMER_INTERVAL:int = 10;
		private var baseTimer:int;
	}
}