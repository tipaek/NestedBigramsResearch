import java.uitil.*;
import java.io.*;
public class Solution{
    public static void main(String[]args){
        Scanner sc=new Scanner(Systenm.in);
        
        
        
		int arr[][]=new int [N][2]; int []sing=new int[N];
for(int i1=0;i1<N;i1++) {
	for(int j=0;j<2;j++) {
		arr[i1][j]=sc.nextInt();
		sing[i1]=arr[i1][0];
	}
}  
		Arrays.sort(arr, new Comparator<int[]>() {
			
			public int compare(int[]a,int[]b) {
				return a[0]-b[0];
			}
		});
		//System.out.println(Arrays.deepToString(arr));
		int check=0;
		for(int i1=0;i1+1<N;i1++) {
			if(arr[i1][1]>arr[i1+1][0] ) {
				check++;
			}
		}  
		if(check==N-1) {
			System.out.println("Case #"+(i+1)+": "+"IMPOSSIBLE");
		}
		else {	
		StringBuilder s=new StringBuilder(); s.append("C"); char curr=s.charAt(0); int k=0;
		
	for(int j=0;j<N;j++) {
	   for(k=j+1;k<N;k++) {	
		if(arr[j][1]>arr[k][0] ) {  
		  if(curr=='C' ) { s.append("J"); 
			  } 
		  
		  else if(curr=='J' ) {s.append("C"); }
		}
		else {
			
			s.append(curr); 
		}
	
	}
	   j=k-1; curr=s.charAt(s.length()-1);  
	}
LinkedList<Character> lc=new LinkedList<Character>();LinkedList<Integer> li=new LinkedList<Integer>();
for(int j=0;j<N;j++) {
	lc.add(j,s.charAt(j)); li.add(j,arr[j][1]);
}
	//System.out.println(lc+" "+li);
		
	Iterator lc1=lc.iterator();
	Iterator li1=li.iterator(); int cc=0,cj=0;
while(lc1.hasNext()) {
	if(lc1.next().equals('C')) {
		cc++;
	}
	else  {
		cj++;
	}
} List<Integer> ty=new LinkedList<Integer>();List<Integer> ty1=new LinkedList<Integer>();
for(int j=0;j<lc.size();j++) {
	if(lc.get(j).equals('C')) {
		ty1.add(li.get(j));
	}
	else if(lc.get(j).equals('J')) { 
		ty.add(li.get(j));
	}
}

for( int j=0;j<ty1.size() ;j++) {
	if(j+1<ty1.size() && ty1.get(j)<ty1.get(j+1)) {
		
	}
	else { int ind=-1;
		if(j+1<ty1.size()) {
		int var=ty1.get(j+1);
		for(int yu=0;yu<li.size();yu++) {
			if(var==li.get(yu)) {
			ind=yu;	
			}
		}
		if(ind>=0) {
			char op=s.charAt(ind);
			if(op=='C') {
				s.deleteCharAt(ind);
				s.insert(ind, 'J');
			}
			
		}
		
		}
	}
}
for( int j=0;j<ty.size() ;j++) {
	if(j+1<ty.size() && ty.get(j)<ty.get(j+1)) {
		
	}
	else { int ind=-1;
		if(j+1<ty.size()) {
		int var=ty.get(j+1);
		for(int yu=0;yu<li.size();yu++) {
			if(var==li.get(yu)) {
			ind=yu;	
			}
		}
		if(ind>=0) {
			char op=s.charAt(ind);
			if(op=='J') {
				s.deleteCharAt(ind);
				s.insert(ind, 'C');
			}
			
		}
		
		}
	}
}//System.out.println(Arrays.deepToString(arr));
StringBuilder v=new StringBuilder(); String gg=""; int []oo=new int[N];
for(int kk=0;kk<N;kk++) {
	oo[kk]=arr[kk][0];
}
for(int p=0;p<N;p++) {
	int y=Arrays.binarySearch(oo,sing[p]);
	v.append(s.charAt(y));
}
//System.out.println(Arrays.toString(sing));// System.out.println(gg);
	System.out.println("Case #"+(i+1)+": "+v);
		
		

	

}
	
	
	

}

    }
    
    
    
    
}