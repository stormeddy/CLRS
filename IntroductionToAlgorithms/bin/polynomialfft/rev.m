function [mid]=rev(n,k)
%   nΪҪλ���������kΪn�Ķ�����λ��
    mid=0;
    while k>0
       mid=bitshift(mid,1);
       bit=bitand(n,1);
       mid=bitor(mid,bit);
       n=bitshift(n,-1);
       k=k-1;
    end
end