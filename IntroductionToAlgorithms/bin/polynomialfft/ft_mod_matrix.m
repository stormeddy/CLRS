function [M]=ft_mod_matrix(w,size,base)
%   base：基于base的模，size：矩阵大小，w：主n次单位根
%   w^0+w^1+w^2+...+w^(n-1) = 0 (mod n)
    M=zeros(size);
    for i=1:size
        for j=1:size
            M(i,j)=modular_exp(w,(i-1)*(j-1),base);
        end
    end
end