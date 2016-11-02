function [y]=recursive_ifft_3(a)
    n=numel(a);
    y=recursive_ifft_help_3(a)/n;
end