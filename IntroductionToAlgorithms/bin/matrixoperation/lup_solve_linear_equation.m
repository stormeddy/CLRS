function [x]=lup_solve_linear_equation(A,b)
%   �����Է���Ax=b
    [L,U,pi]=lup_decomposition(A);
    x=lup_solve(L,U,pi,b);
end