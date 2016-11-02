package basicdatastructure;

public class Exercise_10_2_8 {}
/*	np[x] = next[x] XOR prev[x]，根据异或的性质，next[x] = np[x] XOR prev[x]，prev[x] = next[x] XOR np[x]，
 *	 只要知道这个结点的下一个结点的地址，就能求出它上一个结点的地址，或者，只要知道这个结点上一个结点的位置，就知道求出它的下一个结点的位置
	在链表中定义一个头结点，这个结点的位置是已知的，根据上文的结论，就可以依次遍历链表中的每个结点，进行于SEARCH、INSERT和DELETE操作了
	其中应注意的要点是：
	1.先建立一个链表L，其中表头存放着一个初始结点node head,这是第一个结点，
	隐含着head.prev=0,即head.np=head.prev XOR head.next=head.next。这一点十分重要，它说明了head.np=head.next恒成立。说明我们从一开始就已经知道head.next的地址，即第一个元素地址
	2应注意异或的性质：A XOR A=0,即两个相同的数做异或，它们的结果一定为0.这一点在求各个结点的np时特别有用，
	还有A XOR B=C,则有B=A XOR C和C =BXOR A,即三个数可以通过两两异或得出第三个数。
	3在插入中，新插入的点A的next结点和head的下一个结点相同，这一点我思考了好久才明白，这是为什么呢？
	因为如果链表一开始是空，则head.next=null,此时A插入到表头，则A.next也是null,相等。若一开始链表不为空，假设head.next指向B,现在新插入一个A,那么A.next也必然是指向B
 * 	#include <iostream>  
	#include <string>  
	using namespace std;  
	  
	//结点  
	struct node  
	{  
	    int key;//值  
	    unsigned int np;//指针，地址是32位的，所以要用无符号整数表示  
	    node(int k):key(k),np(0){}  
	};  
	//链表  
	struct list  
	{  
	    node head;//头结点，作为哨兵，用于寻找第一个结点  
	    list():head(0){}  
	};  
	//插入操作  
	void Insert(list *l, int k)  
	{  
	    //生成一个新的结点  
	    node *A = new node(k);  
	    //要插入到链表的第一个位置，即head->next处  
	    //A->next是head->next，其地址为l->head.np，A->prev是head，其地址为(unsigned int)(&(l->head))  
	    //A->np是A->next ^ A->prev  
	    A->np = l->head.np ^ (unsigned int)(&(l->head));  
	    //若原链表不为空，则要修改原head->next结果的指针，因为它的prev发生了改变  
	    node *p = (node*)l->head.np;  
	    if(p)  
	    {  
	        //NewNp = OldNp ^ OldNext ^ OldPrev ^ NewNext ^ NewPrev;  
	        //在这里，只是p->prev有改动，p->next不变，因此简化为NewNp = OldNp ^ OldPrev ^ NewPrev;  
	        //OldPrev是Head，其地址为(unsigned int)(&(l->head))，NewPrev是新结点A，其地址为(unsigned int)A  
	        unsigned int temp = p->np;  
	        temp = temp ^ (unsigned int)A ^ (unsigned int)(&(l->head));  
	        p->np = temp;  
	    }  
	    l->head.np = (unsigned int)A;  
	}  
	//输出链表  
	void Print(list *l)  
	{  
	    //找到第一个结点的位置，因为Head->prev=0，Head->next=Head->np，直接可以得到第一个结点的位置  
	    unsigned int p = l->head.np, q;  
	    //q表示为p->prev结点的位置  
	    q = (unsigned int)(&(l->head));  
	    //把p转换为指针  
	    while((node*)p)  
	    {  
	        //输出  
	        cout<<((node*)p)->key<<' ';  
	        //p指向下一个元素  
	        unsigned int t = p;  
	        p = ((node*)p)->np ^ q;  
	        q = t;  
	    }  
	    cout<<endl;  
	}  
	//删除  
	void Delete(list *l, int k)  
	{  
	    //依次遍历每个元素，遍历方法同Search   
	    unsigned int p = l->head.np, q;  
	    q = (unsigned int)(&(l->head));  
	    while((node*)p)  
	    {  
	        if(((node*)p)->key == k)  
	            break;  
	        unsigned int t = p;  
	        p = ((node*)p)->np ^ q;  
	        q = t;  
	    }  
	    //p是待删除元素的地址，q是待删除元素的上一个元素的地址，r是待删除元素的下一个元素的地址  
	    if((node*)p == NULL)  
	        return;  
	    int r = ((node*)p)->np ^ q;  
	    //修改q->next和修改r->prev，修改方法与INSERT中的修改方法相似  
	    ((node*)q)->np = ((node*)q)->np ^ p ^ r;  
	    if((node*)r != NULL)  
	        ((node*)r)->np = ((node*)r)->np ^ p ^ q;  
	    //删除p指向的结点  
	    delete (node*)p;  
	}  
	int main()  
	{  
	    string str;  
	    list *L = new list;  
	    int x;  
	    while(1)  
	    {  
	        cin>>str;  
	        if(str == "P")  
	        {  
	            Print(L);  
	        }  
	        else if(str == "I")  
	        {  
	            x = rand() % 100;  
	            cout<<x<<endl;  
	            Insert(L, x);  
	        }  
	        else if(str == "D")  
	        {  
	            cin>>x;  
	            Delete(L, x);  
	        }  
	    }  
	    return 0;  
	}  */

