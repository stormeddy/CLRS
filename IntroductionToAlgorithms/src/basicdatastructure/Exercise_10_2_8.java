package basicdatastructure;

public class Exercise_10_2_8 {}
/*	np[x] = next[x] XOR prev[x]�������������ʣ�next[x] = np[x] XOR prev[x]��prev[x] = next[x] XOR np[x]��
 *	 ֻҪ֪�����������һ�����ĵ�ַ�������������һ�����ĵ�ַ�����ߣ�ֻҪ֪����������һ������λ�ã���֪�����������һ������λ��
	�������ж���һ��ͷ��㣬�������λ������֪�ģ��������ĵĽ��ۣ��Ϳ������α��������е�ÿ����㣬������SEARCH��INSERT��DELETE������
	����Ӧע���Ҫ���ǣ�
	1.�Ƚ���һ������L�����б�ͷ�����һ����ʼ���node head,���ǵ�һ����㣬
	������head.prev=0,��head.np=head.prev XOR head.next=head.next����һ��ʮ����Ҫ����˵����head.np=head.next�������˵�����Ǵ�һ��ʼ���Ѿ�֪��head.next�ĵ�ַ������һ��Ԫ�ص�ַ
	2Ӧע���������ʣ�A XOR A=0,��������ͬ������������ǵĽ��һ��Ϊ0.��һ�������������npʱ�ر����ã�
	����A XOR B=C,����B=A XOR C��C =BXOR A,������������ͨ���������ó�����������
	3�ڲ����У��²���ĵ�A��next����head����һ�������ͬ����һ����˼���˺þò����ף�����Ϊʲô�أ�
	��Ϊ�������һ��ʼ�ǿգ���head.next=null,��ʱA���뵽��ͷ����A.nextҲ��null,��ȡ���һ��ʼ����Ϊ�գ�����head.nextָ��B,�����²���һ��A,��ôA.nextҲ��Ȼ��ָ��B
 * 	#include <iostream>  
	#include <string>  
	using namespace std;  
	  
	//���  
	struct node  
	{  
	    int key;//ֵ  
	    unsigned int np;//ָ�룬��ַ��32λ�ģ�����Ҫ���޷���������ʾ  
	    node(int k):key(k),np(0){}  
	};  
	//����  
	struct list  
	{  
	    node head;//ͷ��㣬��Ϊ�ڱ�������Ѱ�ҵ�һ�����  
	    list():head(0){}  
	};  
	//�������  
	void Insert(list *l, int k)  
	{  
	    //����һ���µĽ��  
	    node *A = new node(k);  
	    //Ҫ���뵽����ĵ�һ��λ�ã���head->next��  
	    //A->next��head->next�����ַΪl->head.np��A->prev��head�����ַΪ(unsigned int)(&(l->head))  
	    //A->np��A->next ^ A->prev  
	    A->np = l->head.np ^ (unsigned int)(&(l->head));  
	    //��ԭ����Ϊ�գ���Ҫ�޸�ԭhead->next�����ָ�룬��Ϊ����prev�����˸ı�  
	    node *p = (node*)l->head.np;  
	    if(p)  
	    {  
	        //NewNp = OldNp ^ OldNext ^ OldPrev ^ NewNext ^ NewPrev;  
	        //�����ֻ��p->prev�иĶ���p->next���䣬��˼�ΪNewNp = OldNp ^ OldPrev ^ NewPrev;  
	        //OldPrev��Head�����ַΪ(unsigned int)(&(l->head))��NewPrev���½��A�����ַΪ(unsigned int)A  
	        unsigned int temp = p->np;  
	        temp = temp ^ (unsigned int)A ^ (unsigned int)(&(l->head));  
	        p->np = temp;  
	    }  
	    l->head.np = (unsigned int)A;  
	}  
	//�������  
	void Print(list *l)  
	{  
	    //�ҵ���һ������λ�ã���ΪHead->prev=0��Head->next=Head->np��ֱ�ӿ��Եõ���һ������λ��  
	    unsigned int p = l->head.np, q;  
	    //q��ʾΪp->prev����λ��  
	    q = (unsigned int)(&(l->head));  
	    //��pת��Ϊָ��  
	    while((node*)p)  
	    {  
	        //���  
	        cout<<((node*)p)->key<<' ';  
	        //pָ����һ��Ԫ��  
	        unsigned int t = p;  
	        p = ((node*)p)->np ^ q;  
	        q = t;  
	    }  
	    cout<<endl;  
	}  
	//ɾ��  
	void Delete(list *l, int k)  
	{  
	    //���α���ÿ��Ԫ�أ���������ͬSearch   
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
	    //p�Ǵ�ɾ��Ԫ�صĵ�ַ��q�Ǵ�ɾ��Ԫ�ص���һ��Ԫ�صĵ�ַ��r�Ǵ�ɾ��Ԫ�ص���һ��Ԫ�صĵ�ַ  
	    if((node*)p == NULL)  
	        return;  
	    int r = ((node*)p)->np ^ q;  
	    //�޸�q->next���޸�r->prev���޸ķ�����INSERT�е��޸ķ�������  
	    ((node*)q)->np = ((node*)q)->np ^ p ^ r;  
	    if((node*)r != NULL)  
	        ((node*)r)->np = ((node*)r)->np ^ p ^ q;  
	    //ɾ��pָ��Ľ��  
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

