package com.example.demo.enums;

public class PageCountConstant {
	
	public enum PageCount{
		
		PAGECOUNT;
		
		private static int pageCount;
		
//		public static PageCount getPageCount() {
//			return PAGECOUNT;
//		}

		public static int getPageCount() {
			return pageCount;
		}

		public static void setPageCount(int pageCount1) {
			pageCount = pageCount1;
		}
		
		public static void setPageCountPlusOne() {
			pageCount+=1;
		}
		
		public static void setPageCountMinusOne() {
			pageCount-=1;
			if(pageCount<0) {
				pageCount=0;
			}
		}
	}

}
