function [b]=bit_reverse_copy(a)
%   Î»ÄæĞòÖÃ»»
    n=numel(a);
    b=zeros(n,1);
    bit_num=log2(n);
    for k=1:n
        b(rev(k-1,bit_num)+1)=a(k);
    end
end