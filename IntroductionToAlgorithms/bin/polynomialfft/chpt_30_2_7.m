function [y]=chpt_30_2_7(z)
%   求解根为向量z的多项式的系数
    num=numel(z);
    pow=nextpow2(num);
    zero_num=2^pow-num;
    z=[z zeros(1,zero_num)];%补零至2的幂次
    y=chpt_30_2_7_help(z);
    y=y(zero_num+1:zero_num+num+1);
end