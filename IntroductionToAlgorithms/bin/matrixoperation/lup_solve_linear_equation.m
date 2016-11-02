function [x]=lup_solve_linear_equation(A,b)
%   解线性方程Ax=b
    [L,U,pi]=lup_decomposition(A);
    x=lup_solve(L,U,pi,b);
end