function [y]=chpt_30_2_7(z)
%   ����Ϊ����z�Ķ���ʽ��ϵ��
    num=numel(z);
    pow=nextpow2(num);
    zero_num=2^pow-num;
    z=[z zeros(1,zero_num)];%������2���ݴ�
    y=chpt_30_2_7_help(z);
    y=y(zero_num+1:zero_num+num+1);
end