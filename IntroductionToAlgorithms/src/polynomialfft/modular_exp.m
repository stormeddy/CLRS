function [d]=modular_exp(a,b,n)
%   模取幂运算，计算 a^b mod n
    c=0;
    d=1;
    a=mod(a,n);
    str=dec2bin(b);
    for i=1:numel(str)
        c=c*2;
        d=mod(d*d,n);
        if str(i)=='1'
            c=c+1;
            d=mod(d*a,n);
        end
    end    
end