function [y]=chpt_30_2_7_help(z)
%   ����Ϊ����z�Ķ���ʽ��ϵ����z��Ԫ�ظ���Ϊ2���ݴ�
    n=numel(z);
    y=zeros(2*n,1);
    if n==2        
        y(1)=z(1)*z(2);
        y(2)=-z(1)-z(2);
        y(3)=1;
        return;
    end
    y1=chpt_30_2_7_help(z(1:n/2));
    y1=[y1;zeros(n,1)];
    y2=chpt_30_2_7_help(z(n/2+1:n));
    y2=[y2;zeros(n,1)];
    y=polynomial_multiplication(y1,y2);
end