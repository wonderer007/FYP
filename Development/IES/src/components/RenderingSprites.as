package components
{
	import flash.display.Bitmap;
	import flash.display.BitmapData;
	import flash.geom.Point;
	import flash.geom.Rectangle;
	
	import mx.core.UIComponent;
	
	[SWF(width=1000, height=1000, frameRate=20, backgroundColor=0xE2E2E2)]
	
	public class RenderingSprites extends UIComponent
	{
		public function RenderingSprites(xStart:Number, yStart:Number, xSize:Number, ySize:Number)
		{
			xBoundarySize = xSize - xStart;
			yBoundarySize = ySize - yStart;
			
			sX=xStart;
			sY=yStart;
			
			
			newXCor = prevXCor= int (xBoundarySize / 2);
			newYCor = prevYCor= int (yBoundarySize / 2);
			stateDesc = 0;
			
			imageVal=0;								//pointing to the first tile in the sprite sheet
			
			imageXVal = int ( imageVal % 3 ) * 31;
			imageYVal = int ( imageVal / 3 ) * 32;
			
			spritesheet = (new spriteSheetSource() as Bitmap).bitmapData;
			
			canvas =  new BitmapData(xBoundarySize, yBoundarySize, true, 0xFFFFFF);
			uiComponent=new UIComponent();
			
			uiComponent.addChild(new Bitmap(canvas));
			
			addChild(uiComponent);
			 
			rect = new Rectangle(0, 0, 31,31);
			tempRect = new Rectangle(imageYVal,imageXVal,1000,1000);
			
			canvas.lock();
			rect.x = imageXVal;
			rect.y = imageYVal;
			
			canvas.fillRect(tempRect, 0xFFFFFF);
			canvas.copyPixels(spritesheet,rect,new Point(newXCor,newYCor));
			
			canvas.unlock();
		}
		
		public function spriteEventHandler(stageXCor:Number, stageYCor:Number):void{
			
			var px:Number = stageXCor - prevXCor ;
			var py:Number = stageYCor - prevYCor;
			var valToAddInX:Number = 0;
			var valToAddInY:Number = 0;
			
			if((px < 0 && py < 0) || (px < 0 && py > 0) || (px < 0 && py == 0)){
				
				if(stateDesc != 0){
					imageVal = 3;
				}
				
				valToAddInX = -1;
				
				if(py < 0){
					valToAddInY = -1;
				}
				else{
					valToAddInY = 1;
				}
				
				stateDesc = 0;
			}
			else if((px > 0 && py > 0) || (px > 0 && py < 0) || (px > 0 && py == 0)){
				
				if(stateDesc != 1){
					imageVal = 6;
				}
				
				valToAddInX = 1;
				
				if(py < 0){
					valToAddInY = -1;
				}
				else{
					valToAddInY = 1;
				}
				
				stateDesc = 1;
			}
			else if(px == 0 && py >0){
				
				if(stateDesc != 2){
					imageVal = 0;
				}
				
				valToAddInX = 0;
				valToAddInY = 1;
				
				stateDesc = 2;
			
			}
			else{
				
				if(stateDesc != 3){
					imageVal = 9;
				}
				
				valToAddInX = 0;
				valToAddInY = -1;
				
				stateDesc = 3;
			}
			
			if((newXCor + valToAddInX < xBoundarySize-27) && (newYCor + valToAddInY < yBoundarySize-30) && (newXCor + valToAddInX > sX) && (newYCor + valToAddInY > sY)){
				prevXCor = newXCor = newXCor + valToAddInX;
				prevYCor = newYCor = newYCor + valToAddInY;
				isBoundaryChanged  = false;
				
			}
			else{
				isBoundaryChanged = true;
				if((newXCor + valToAddInX > xBoundarySize-27) && (newYCor + valToAddInY < sY)){
					boundaryState=1;
				}
				else{
					boundaryState=0;
				}
			}
			
			++distanceCovered;
			
			canvas.lock();
			
			imageXVal = int (imageVal % 3) * 31;
			imageYVal = int ( imageVal / 3 ) * 32;
	
			canvas.fillRect(tempRect, 0xFFFFFF);
			
			rect.x = imageXVal;
			rect.y = imageYVal;
			
			canvas.copyPixels(spritesheet,rect,new Point(newXCor,newYCor));
			
			canvas.unlock();
			
			imageVal = imageVal + 1;
			
			if(imageVal == 3){
				imageVal = 0
			}
			else if(imageVal == 6){
				imageVal = 3;
			}
			else if(imageVal == 9){
				imageVal = 6;
			}
			else if(imageVal == 12){
				imageVal = 9;
			}
		}
		
		public function getCurrCor():Point{
			return (new Point(newXCor, newYCor));
		}
		
		public function getBoundaryState():Number{
			return boundaryState;
		}
		
		public function setBoundaryChanged():void{
			isBoundaryChanged = false;
		}
		
		public function getBoundaryChanged():Boolean{
			return isBoundaryChanged;
		}
		
		public function getDistanceCovered():Number{
			return distanceCovered;
		}

		
		public function setDistanceCovered():void{
			 distanceCovered=0;
		}
		
		[Embed(source="char113.png")]
		public var spriteSheetSource:Class;
		public var canvas:BitmapData;
		public var spritesheet:BitmapData;
		public var rect:Rectangle;
		public var tempRect:Rectangle;
		public var uiComponent:UIComponent;
		
		private var prevXCor:Number;
		private var prevYCor:Number;
		private var newXCor:Number;
		private var newYCor:Number;
		private var stateDesc:Number;
		private var sX:Number;
		private var sY:Number;
		
		private var xBoundarySize:Number;
		private var yBoundarySize:Number;
		
		public var imageVal:Number;
		
		private var imageXVal:Number;
		private var imageYVal:Number;
		
		private var boundaryState:Number = 0;
		private var isBoundaryChanged:Boolean = false;
		private var distanceCovered:Number = 0;
	}
}