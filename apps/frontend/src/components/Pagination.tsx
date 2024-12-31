import { Button, Flex, IconButton } from "@chakra-ui/react";
import { ChevronLeftIcon, ChevronRightIcon } from "@chakra-ui/icons";
import { FC } from "react";

interface PaginationProps {
  currentPage: number;
  totalPages: number;
  onPageChange: (page: number) => void;
}

const Pagination: FC<PaginationProps> = ({
  currentPage,
  totalPages,
  onPageChange,
}) => {
  const maxVisiblePages = 5;
  const startPage = Math.max(
    1,
    Math.min(
      currentPage - Math.floor(maxVisiblePages / 2),
      totalPages - maxVisiblePages + 1,
    ),
  );
  const endPage = Math.min(startPage + maxVisiblePages - 1, totalPages);

  return (
    <Flex justifyContent="center" mt={4} alignItems="center">
      <IconButton
        icon={<ChevronLeftIcon />}
        aria-label="Previous Page"
        onClick={() => onPageChange(currentPage - 1)}
        isDisabled={currentPage === 1}
        variant="outline"
        mx={1}
      />
      {Array.from(
        { length: endPage - startPage + 1 },
        (_, i) => startPage + i,
      ).map((page) => (
        <Button
          key={page}
          onClick={() => onPageChange(page)}
          variant={currentPage === page ? "solid" : "outline"}
          colorScheme={"blue"}
          mx={1}
        >
          {page}
        </Button>
      ))}
      <IconButton
        icon={<ChevronRightIcon />}
        aria-label="Next Page"
        onClick={() => onPageChange(currentPage + 1)}
        isDisabled={currentPage === totalPages}
        variant="outline"
        mx={1}
      />
    </Flex>
  );
};

export default Pagination;
