export interface ErrorDTO {
  message: string;
  code: number;
  details?: Record<string, string>;
}
