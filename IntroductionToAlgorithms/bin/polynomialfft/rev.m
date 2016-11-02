function [mid]=rev(n,k)
%   n为要位逆序的数，k为n的二进制位数
    mid=0;
    while k>0
       mid=bitshift(mid,1);
       bit=bitand(n,1);
       mid=bitor(mid,bit);
       n=bitshift(n,-1);
       k=k-1;
    end
end