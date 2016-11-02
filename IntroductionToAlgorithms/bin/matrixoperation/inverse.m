function [X]=inverse(A)
%   使用lup分解求解(伪)逆矩阵
%   当求伪逆矩阵时，若A'*A接近奇异矩阵，会有数值不稳定的情形出现
    [m,n]=size(A);
    if m==n
        X=zeros(n);
        I=eye(n);
        for i=1:n
            X(:,i)=lup_solve_linear_equation(A,I(:,i));
        end
    else
        X=inverse(A'*A)*A';
    end    
end